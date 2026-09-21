package nd.tpe.impl;

import nd.tpe.api.adapter.IMovementInputAdapter;
import nd.tpe.api.adapter.MutableClientInput;
import net.minecraft.class_10185;
import net.minecraft.class_241;
import net.minecraft.class_744;

public record MovementInputAdapter(class_744 input) implements IMovementInputAdapter {
    public boolean isLeftKeyDown() {
        return this.input.field_54155.comp_3161();
    }

    public void setLeftKeyDown(boolean value) {
        this.input.field_54155 = new class_10185(this.input.field_54155.comp_3159(), this.input.field_54155.comp_3160(), value, this.input.field_54155.comp_3162(), this.input.field_54155.comp_3163(), this.input.field_54155.comp_3164(), this.input.field_54155.comp_3165());
    }

    public boolean isRightKeyDown() {
        return this.input.field_54155.comp_3162();
    }

    public void setRightKeyDown(boolean value) {
        this.input.field_54155 = new class_10185(this.input.field_54155.comp_3159(), this.input.field_54155.comp_3160(), this.input.field_54155.comp_3161(), value, this.input.field_54155.comp_3163(), this.input.field_54155.comp_3164(), this.input.field_54155.comp_3165());
    }

    public boolean isForwardKeyDown() {
        return this.input.field_54155.comp_3159();
    }

    public void setForwardKeyDown(boolean value) {
        this.input.field_54155 = new class_10185(value, this.input.field_54155.comp_3160(), this.input.field_54155.comp_3161(), this.input.field_54155.comp_3162(), this.input.field_54155.comp_3163(), this.input.field_54155.comp_3164(), this.input.field_54155.comp_3165());
    }

    public boolean isBackKeyDown() {
        return this.input.field_54155.comp_3160();
    }

    public void setBackKeyDown(boolean value) {
        this.input.field_54155 = new class_10185(this.input.field_54155.comp_3159(), value, this.input.field_54155.comp_3161(), this.input.field_54155.comp_3162(), this.input.field_54155.comp_3163(), this.input.field_54155.comp_3164(), this.input.field_54155.comp_3165());
    }

    public float getMoveForward() {
        return this.input.method_3128().field_1342;
    }

    public void setMoveForward(float value) {
        class_241 moveVector = new class_241(this.input.method_3128().field_1343, value);
        ((MutableClientInput)this.input).betterThirdPerson$setMoveVector(moveVector);
    }

    public float getMoveStrafe() {
        return this.input.method_3128().field_1343;
    }

    public void setMoveStrafe(float value) {
        class_241 moveVector = new class_241(value, this.input.method_3128().field_1342);
        ((MutableClientInput)this.input).betterThirdPerson$setMoveVector(moveVector);
    }
}