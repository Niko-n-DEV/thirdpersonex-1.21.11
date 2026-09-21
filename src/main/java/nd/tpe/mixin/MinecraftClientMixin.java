package nd.tpe.mixin;

import nd.tpe.ThirdPersonEx;
import nd.tpe.api.action.ItemRepeatableUseAction;
import nd.tpe.api.action.MouseAction;
import nd.tpe.impl.ClientAdapter;
import nd.tpe.impl.PlayerAdapter;
import net.minecraft.class_310;
import net.minecraft.class_315;
import net.minecraft.class_5498;
import net.minecraft.class_746;
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

@Mixin({class_310.class})
public abstract class MinecraftClientMixin {
    @Shadow
    private int field_1752;
    @Shadow
    @Final
    public class_315 field_1690;

    @Shadow
    protected abstract boolean method_1536();

    @Shadow
    protected abstract void method_1583();

    @Shadow
    protected abstract void method_1590(boolean var1);

    @Inject(
            method = {"handleKeybinds"},
            at = {@At("HEAD")}
    )
    public void onHandleInputEvents(CallbackInfo ci) {
        PlayerAdapter player = new PlayerAdapter(class_310.method_1551().field_1724);
        ThirdPersonEx.getCameraManager().onInputEvents(player);
    }

    @Inject(
            method = {"runTick"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V",
                    shift = Shift.BEFORE
            )},
            slice = {@Slice(
                    from = @At(
                            value = "FIELD",
                            opcode = 180,
                            target = "Lnet/minecraft/client/Minecraft;noRender:Z",
                            shift = Shift.AFTER
                    ),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V",
                            shift = Shift.BEFORE
                    )
            )}
    )
    public void preRenderHook(boolean tick, CallbackInfo ci) {
        class_746 player = class_310.method_1551().field_1724;
        if (player != null) {
            PlayerAdapter playerAdapter = new PlayerAdapter(player);
            float tickDelta = class_310.method_1551().method_61966().method_60637(true);
            ThirdPersonEx.getCameraManager().onRenderTickStart(playerAdapter, tickDelta);
        }
    }

    @Inject(
            method = {"startAttack"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void onDoAttack(CallbackInfoReturnable<Boolean> cir) {
        class_746 player = class_310.method_1551().field_1724;
        if (player != null) {
            PlayerAdapter playerAdapter = new PlayerAdapter(player);
            MouseAction action = new MouseAction(this::method_1536);
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
        class_746 player = class_310.method_1551().field_1724;
        if (player != null) {
            PlayerAdapter playerAdapter = new PlayerAdapter(player);
            MouseAction action = new MouseAction(() -> this.method_1590(true));
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
    public void onDoItemUse(class_310 client) {
        class_746 player = client.field_1724;
        if (player != null) {
            PlayerAdapter playerAdapter = new PlayerAdapter(player);
            MouseAction action = new MouseAction(this::method_1583);
            if (!ThirdPersonEx.getCameraManager().onMouseAction(playerAdapter, action)) {
                this.method_1583();
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
    public void onItemUseRepeatable(class_310 client) {
        class_746 player = client.field_1724;
        if (player != null) {
            PlayerAdapter playerAdapter = new PlayerAdapter(player);
            ItemRepeatableUseAction action = new ItemRepeatableUseAction(ClientAdapter.INSTANCE, () -> this.field_1752, this::method_1583);
            if (!ThirdPersonEx.getCameraManager().onMouseAction(playerAdapter, action)) {
                this.method_1583();
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
            if (this.field_1690.method_31044() == class_5498.field_26666) {
                this.field_1690.method_31043(class_5498.field_26664);
            }

        }
    }
}