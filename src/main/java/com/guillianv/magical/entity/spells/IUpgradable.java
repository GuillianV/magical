package com.guillianv.magical.entity.spells;

import net.minecraft.network.chat.Component;

import java.util.List;

public interface IUpgradable {


    void Upgrade(int level);

    List<UpgradeProperty> ShowProperties();
}
