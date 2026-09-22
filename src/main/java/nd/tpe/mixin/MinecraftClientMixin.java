package nd.tpe.mixin;

import nd.tpe.ThirdPersonEx;
import nd.tpe.api.action.ItemRepeatableUseAction;
import nd.tpe.api.action.MouseAction;
import nd.tpe.impl.ClientAdapter;
import nd.tpe.impl.PlayerAdapter;
import net.minecraft.client.Minecraft; // class_310;
import net.minecraft.client.Options; // class_315;
import net.minecraft.client.CameraType; // class_5498;
import net.minecraft.client.player.LocalPlayer; // class_746;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Minecraft.class})
public abstract class MinecraftClientMixin {
    @Shadow
    private int rightClickDelay;
    @Shadow
    @Final
    public Options options;

    @Shadow
    protected abstract boolean startAttack();

    @Shadow
    protected abstract void startUseItem();

    @Shadow
    protected abstract void continueAttack(boolean var1);

    @Inject(
            method = {"handleKeybinds"},
            at = {@At("HEAD")}
    )
    public void onHandleInputEvents(CallbackInfo ci) {
        PlayerAdapter player = new PlayerAdapter(Minecraft.getInstance().player);
        ThirdPersonEx.getCameraManager().onInputEvents(player);
    }

    @Inject(
            method = "runTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V",
                    shift = Shift.BEFORE
            )
    )

    public void preRenderHook(boolean tick, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            PlayerAdapter playerAdapter = new PlayerAdapter(player);
            float tickDelta = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true);
            ThirdPersonEx.getCameraManager().onRenderTickStart(playerAdapter, tickDelta);
        }
    }

    @Inject(
            method = {"startAttack"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void onDoAttack(CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            PlayerAdapter playerAdapter = new PlayerAdapter(player);
            MouseAction action = new MouseAction(this::startAttack);
            if (ThirdPersonEx.getCameraManager().onMouseAction(playerAdapter, action)) {
                cir.setReturnValue(false);
            }

        }
    }

    @Inject(
            method = {"continueAttack"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void onBlockBreaking(boolean pressed, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            PlayerAdapter playerAdapter = new PlayerAdapter(player);
            MouseAction action = new MouseAction(() -> this.continueAttack(true));
            if (pressed && ThirdPersonEx.getCameraManager().onMouseAction(playerAdapter, action)) {
                ci.cancel();
            }

        }
    }

    @Redirect(
            method = {"handleKeybinds"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;startUseItem()V",
                    ordinal = 0
            )
    )
    public void onDoItemUse(Minecraft client) {
        LocalPlayer player = client.player;
        if (player != null) {
            PlayerAdapter playerAdapter = new PlayerAdapter(player);
            MouseAction action = new MouseAction(this::startUseItem);
            if (!ThirdPersonEx.getCameraManager().onMouseAction(playerAdapter, action)) {
                this.startUseItem();
            }

        }
    }

    @Redirect(
            method = {"handleKeybinds"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;startUseItem()V",
                    ordinal = 1
            )
    )
    public void onItemUseRepeatable(Minecraft client) {
        LocalPlayer player = client.player;
        if (player != null) {
            PlayerAdapter playerAdapter = new PlayerAdapter(player);
            ItemRepeatableUseAction action = new ItemRepeatableUseAction(ClientAdapter.INSTANCE, () -> this.rightClickDelay, this::startUseItem);
            if (!ThirdPersonEx.getCameraManager().onMouseAction(playerAdapter, action)) {
                this.startUseItem();
            }

        }
    }

    @Inject(
            method = {"handleKeybinds"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Options;setCameraType(Lnet/minecraft/client/CameraType;)V",
                    shift = Shift.AFTER
            )}
    )
    public void onSetPerspective(CallbackInfo ci) {
        if (ThirdPersonEx.getCameraManager().getConfig().skipThirdPersonFrontView()) {
            if (this.options.getCameraType() == CameraType.THIRD_PERSON_FRONT) {
                this.options.setCameraType(CameraType.THIRD_PERSON_FRONT);
            }

        }
    }
}