function initializeCoreMod() {
    var ASM_HOOKS = "svenhjol/charm/base/CharmAsmHooks";
    var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
    var Opcodes = Java.type('org.objectweb.asm.Opcodes');
    var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
    var InsnList = Java.type('org.objectweb.asm.tree.InsnList');
    var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
    var IincInsnNode = Java.type('org.objectweb.asm.tree.IincInsnNode');
    var FieldInsnNode = Java.type('org.objectweb.asm.tree.FieldInsnNode');
    var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
    var JumpInsnNode = Java.type('org.objectweb.asm.tree.JumpInsnNode');
    var LabelNode = Java.type('org.objectweb.asm.tree.LabelNode');

    return {
        'remove_potion_glint': {
            target: {
                'type': 'METHOD',
                'class': 'net.minecraft.item.PotionItem',
                'methodName': 'func_77636_d', // hasEffect
                'methodDesc': '(Lnet/minecraft/item/ItemStack;)Z'
            },
            transformer: function(method) {
                var success = false;
                var newInstructions = new InsnList();
                var arrayLength = method.instructions.size();

                for (var i = 0; i < arrayLength; ++i) {
                    var instruction = method.instructions.get(i);
                    if (instruction.getOpcode() == Opcodes.ALOAD) {
                        var label = new LabelNode();
                        newInstructions.add(ASM.buildMethodCall(ASM_HOOKS, "removePotionGlint", "()Z", ASM.MethodType.STATIC))
                        newInstructions.add(new JumpInsnNode(Opcodes.IFEQ, label));
                        newInstructions.add(new InsnNode(Opcodes.ICONST_0));
                        newInstructions.add(new InsnNode(Opcodes.IRETURN));
                        newInstructions.add(label);

                        method.instructions.insertBefore(instruction, newInstructions);
                        success = true;
                        break;
                    }
                }

                print("[Charm ASM] (RemovePotionGlint) " + (success ? "Transformed PotionItem" : "Failed to transform PotionItem"));
                return method;
            }
        }
    }
}