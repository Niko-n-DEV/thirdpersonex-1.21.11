package nd.tpe.impl;

import nd.tpe.api.adapter.IMovementInputAdapter;
import nd.tpe.api.adapter.MutableClientInput;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.player.ClientInput; // class_744
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2; // class_241

public record MovementInputAdapter(ClientInput input) implements IMovementInputAdapter {
    public boolean isLeftKeyDown() {
//        return this.input.field_54155.comp_3161();
        return this.input.keyPresses.left();
    }

    public void setLeftKeyDown(boolean value) {
//        this.input.field_54155 = new class_10185(this.input.field_54155.comp_3159(), this.input.field_54155.comp_3160(), value, this.input.field_54155.comp_3162(), this.input.field_54155.comp_3163(), this.input.field_54155.comp_3164(), this.input.field_54155.comp_3165());
        this.input.keyPresses = new Input(
                this.input.keyPresses.forward(),
                this.input.keyPresses.backward(),
                value,
                this.input.keyPresses.right(),
                this.input.keyPresses.jump(),
                this.input.keyPresses.shift(),
                this.input.keyPresses.sprint()
        );
    }

    public boolean isRightKeyDown() {
        return this.input.keyPresses.right();
//        return this.input.field_54155.comp_3162();
    }

    public void setRightKeyDown(boolean value) {
        this.input.keyPresses = new Input(
                this.input.keyPresses.forward(),
                this.input.keyPresses.backward(),
                this.input.keyPresses.right(),
                value,
                this.input.keyPresses.jump(),
                this.input.keyPresses.shift(),
                this.input.keyPresses.sprint()
        );
//        this.input.field_54155 = new class_10185(this.input.field_54155.comp_3159(), this.input.field_54155.comp_3160(), this.input.field_54155.comp_3161(), value, this.input.field_54155.comp_3163(), this.input.field_54155.comp_3164(), this.input.field_54155.comp_3165());
    }

    public boolean isForwardKeyDown() {
        return this.input.keyPresses.forward();
//        return this.input.field_54155.comp_3159();
    }

    public void setForwardKeyDown(boolean value) {
        this.input.keyPresses = new Input(
                value,
                this.input.keyPresses.backward(),
                this.input.keyPresses.left(),
                this.input.keyPresses.right(),
                this.input.keyPresses.jump(),
                this.input.keyPresses.shift(),
                this.input.keyPresses.sprint()
        );
//        this.input.field_54155 = new class_10185(value, this.input.field_54155.comp_3160(), this.input.field_54155.comp_3161(), this.input.field_54155.comp_3162(), this.input.field_54155.comp_3163(), this.input.field_54155.comp_3164(), this.input.field_54155.comp_3165());
    }

    public boolean isBackKeyDown() {
        return this.input.keyPresses.backward();
//        return this.input.field_54155.comp_3160();
    }

    public void setBackKeyDown(boolean value) {
        this.input.keyPresses = new Input(
                this.input.keyPresses.forward(),
                value,
                this.input.keyPresses.left(),
                this.input.keyPresses.right(),
                this.input.keyPresses.jump(),
                this.input.keyPresses.shift(),
                this.input.keyPresses.sprint()
        );
//        this.input.field_54155 = new class_10185(this.input.field_54155.comp_3159(), value, this.input.field_54155.comp_3161(), this.input.field_54155.comp_3162(), this.input.field_54155.comp_3163(), this.input.field_54155.comp_3164(), this.input.field_54155.comp_3165());
    }

    public float getMoveForward() {
        return this.input.getMoveVector().y;
//        return this.input.method_3128().field_1342;
    }

    public void setMoveForward(float value) {
        Vec2 moveVector = new Vec2(this.input.getMoveVector().x, value);
        ((MutableClientInput)this.input).betterThirdPerson$setMoveVector(moveVector);
    }

    public float getMoveStrafe() {
        return this.input.getMoveVector().x;
    }

    public void setMoveStrafe(float value) {
        Vec2 moveVector = new Vec2(value, this.input.getMoveVector().y);
        ((MutableClientInput)this.input).betterThirdPerson$setMoveVector(moveVector);
    }
}