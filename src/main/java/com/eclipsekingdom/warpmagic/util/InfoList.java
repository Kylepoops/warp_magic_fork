package com.eclipsekingdom.warpmagic.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

import static com.eclipsekingdom.warpmagic.sys.lang.Message.LABEL_PAGE;

public class InfoList {

    public InfoList(String title, List<String> items, int pageItemCount) {
        this.title = title;
        this.items = items;
        this.pageItemCount = pageItemCount;
    }

    public void displayTo(CommandSender sender, int page) {
        if (page < 1) {
            page = 1;
        }
        sender.sendMessage(title);
        sender.sendMessage(ChatColor.DARK_GRAY + "> " + LABEL_PAGE + " " + page);

        int startIndex = (page - 1) * pageItemCount;
        for (int i = startIndex; i < startIndex + pageItemCount; i++) {
            int num = i + 1;
            String prefix = num + ". ";
            if (i < items.size()) {
                sender.sendMessage(prefix + items.get(i));
            } else {
                sender.sendMessage(prefix + "-");
            }
        }
    }

    private final String title;
    private final List<String> items;
    private final int pageItemCount;

}
