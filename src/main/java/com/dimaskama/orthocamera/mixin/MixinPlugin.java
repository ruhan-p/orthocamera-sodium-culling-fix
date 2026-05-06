package com.dimaskama.orthocamera.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {

    private boolean sodiumPresent;

    @Override
    public void onLoad(String mixinPackage) {
        sodiumPresent = getClass().getClassLoader().getResource(
            "net/caffeinemc/mods/sodium/client/render/chunk/occlusion/OcclusionCuller.class"
        ) != null;

        LoggerFactory.getLogger("OrthoCamera").info(
            "[OrthoCamera/MixinPlugin] Sodium detected: {}. Sodium compatibility mixins will {}be applied.",
            sodiumPresent, sodiumPresent ? "" : "NOT "
        );
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.startsWith("com.dimaskama.orthocamera.mixin.sodium.")) {
            return sodiumPresent;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
