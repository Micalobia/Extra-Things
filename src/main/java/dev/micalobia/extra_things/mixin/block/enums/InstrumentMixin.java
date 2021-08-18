package dev.micalobia.extra_things.mixin.block.enums;

import dev.micalobia.extra_things.block.enums.ModdedInstrument;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.Instrument;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(Instrument.class)
public abstract class InstrumentMixin {
	@SuppressWarnings("ShadowTarget")
	@Shadow
	@Final
	@Mutable
	private static Instrument[] field_12652;

	@SuppressWarnings({"InvokerTarget", "SameParameterValue"})
	@Invoker("<init>")
	private static Instrument create(String internalName, int internalId, String name, SoundEvent sound) {
		throw new AssertionError();
	}

	@SuppressWarnings("UnresolvedMixinReference")
	@Inject(method = "<clinit>", at = @At(
			value = "FIELD",
			opcode = Opcodes.PUTSTATIC,
			target = "Lnet/minecraft/block/enums/Instrument;field_12652:[Lnet/minecraft/block/enums/Instrument;",
			shift = Shift.AFTER
	))
	private static void addCustomInstrument(CallbackInfo ci) {
		List<Instrument> instruments = new ArrayList<>(Arrays.asList(field_12652));
		Instrument last = instruments.get(instruments.size() - 1);
		int last_ordinal = last.ordinal();
		ModdedInstrument.AMETHYST = create("AMETHYST", ++last_ordinal, "amethyst", SoundEvents.BLOCK_NOTE_BLOCK_CHIME);
		instruments.add(ModdedInstrument.AMETHYST);
		field_12652 = instruments.toArray(new Instrument[0]);
	}

	@Inject(method = "fromBlockState", at = @At("HEAD"), cancellable = true)
	private static void checkCustomInstruments(BlockState state, CallbackInfoReturnable<Instrument> cir) {
		if(state.isOf(Blocks.AMETHYST_BLOCK)) {
			cir.setReturnValue(ModdedInstrument.AMETHYST);
		}
	}
}
