package com.blakebr0.ironjetpacks.compact;

import com.blakebr0.ironjetpacks.config.ModConfig;
import com.blakebr0.ironjetpacks.handler.KeybindHandler;
import com.mrcrayfish.controllable.Controllable;
import com.mrcrayfish.controllable.client.ButtonBindings;
import com.mrcrayfish.controllable.client.Controller;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class ControllableCompat {
    private static boolean up = false;
    private static boolean down = false;
    private static boolean forwards = false;
    private static boolean backwards = false;
    private static boolean left = false;
    private static boolean right = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.getConnection() == null)
                return;

            Controller controller = Controllable.getController();

            boolean forwardsNow = controller != null && controller.getLThumbStickYValue() < -0.5;
            boolean backwardsNow = controller != null && controller.getLThumbStickYValue() > 0.5;
            boolean leftNow = controller != null && controller.getLThumbStickXValue() < -0.5;
            boolean rightNow = controller != null && controller.getLThumbStickXValue() > 0.5;

            boolean upNow = ButtonBindings.JUMP.isButtonDown();
            boolean downNow = ButtonBindings.SNEAK.isButtonDown();

            if (upNow != up || downNow != down || forwardsNow != forwards || backwardsNow != backwards || leftNow != left || rightNow != right) {
                up = upNow;
                down = downNow;
                forwards = forwardsNow;
                backwards = backwardsNow;
                left = leftNow;
                right = rightNow;

                KeybindHandler.update(up, down, forwards, backwards, left, right);
            }
        }
    }

    public static void register() {
        if(ModConfig.isControllableInstalled())
        {
            MinecraftForge.EVENT_BUS.register(new ControllableCompat());
        }
    }
}