package com.eclipsekingdom.warpmagic.sys.lang;

import org.bukkit.ChatColor;

public enum Message {

    HELP_SPAWN("Help - spawn", "teleport to spawn"),
    HELP_HUB("Help - hub", "teleport to hub"),
    HELP_TPA("Help - tpa", "request to teleport to player"),
    HELP_TPAHERE("Help - tpahere", "request that a player teleport to you"),
    HELP_HOME("Help - home", "show home commands"),
    HELP_WARP("Help - warp", "show warp commands"),
    HELP_VORTEX("Help - vortex", "show vortex commands"),
    HELP_SETSPAWN("Help - setspawn", "set spawn point"),
    HELP_SETHUB("Help - sethub", "set hub point"),
    HELP_DELSPAWN("Help - delspawn", "delete spawn point"),
    HELP_DELHUB("Help - delhub", "delete hub point"),
    HELP_WARPSTONE("Help - warpstone", "give player warp stones"),
    HELP_VORTEXSTONE("Help - vortexstone", "give player vortex stones"),
    HELP_TOWARP("Help - to warp", "teleport to warp"),
    HELP_WARP_SET("Help - warp set", "set or update warp"),
    HELP_WARP_DEL("Help - warp del", "delete warp"),
    HELP_WARP_LIST("Help - warp list", "list warps"),
    HELP_TOVORTEX("Help - to vortex", "teleport to vortex"),
    HELP_VORTEX_SET("Help - vortex set", "set or update vortex"),
    HELP_VORTEX_SETSERVER("Help - vortex setserver", "set or update server vortex"),
    HELP_VORTEX_DEL("Help - vortex del", "delete vortex"),
    HELP_VORTEX_LIST("Help - vortex list", "list all vortexes"),
    HELP_VORTEX_MYLIST("Help - vortex mylist", "list all vortexes set by you"),
    HELP_TOHOME("Help - to home", "teleport home"),
    HELP_HOME_SET("Help - home set", "set or update home"),
    HELP_HOME_DEL("Help - home del", "delete home"),
    HELP_HOME_INVITE("Help - home invite", "invite player to home"),
    HELP_HOME_UNINVITE("Help - home uninvite", "uninvite player from home"),
    HELP_HOME_FLIST("Help - home flist", "list home friends"),
    HELP_HOME_FCLEAR("Help - home fclear", "clear home friends"),
    HELP_TOFHOME("Help - to fhome", "teleport to friend's home"),
    HELP_FHOME_LIST("Help - fhome list", "list homes you are invited to"),
    HELP_JUMP("Help - jump", "teleport to target block"),
    HELP_TOP("Help - top", "teleport to highest block"),
    HELP_BOTTOM("Help - bottom", "teleport to lowest block"),

    ARG_PLAYER("Arg - player", "player"),
    ARG_AMOUNT("Arg - amount", "amount"),

    LABEL_FRIENDS("Label - friends", "Friends"),
    LABEL_FRIEND_HOMES("Label - friend homes", "Friend Homes"),
    LABEL_PAGE("Label - page", "Page"),
    LABEL_COMMANDS("Label - commands", "Commands"),

    INVALID_CHARACTERS("Invalid - special characters", "Names must consist of only a-z, A-Z, 0-9, _, and -"),
    INVALID_TOO_LONG("Invalid - too long", "Names must be 20 characters or less"),
    INVALID_RESERVED_WORD("Invalid - reserved word", "The name you selected is reserved by WarpMagic"),

    REQUEST_TPA("Request - tpa", "%player% has requested to teleport to you."),
    REQUEST_TPAHERE("Request - tpahere", "You have been requested to teleport to %player%."),
    REQUEST_COMMANDS("Request - commands", "Use /tpaccept to accept or /tpdeny to deny the request."),
    REQUEST_TIMEOUT("Request - timeout", "Request is valid for %seconds% seconds."),
    REQUEST_SENT("Request - send", "Request sent to %player%."),

    SUCCESS_HOME_SET("Success - home set", "home set"),
    SUCCESS_HOME_UPDATE("Success - home update", "home updated"),
    SUCCESS_HOME_DELETE("Success - home delete", "home deleted"),
    SUCCESS_HOME_INVITE("Success - home invite", "%player% was invited to your home"),
    SUCCESS_HOME_INVITATION("Success - home invitation", "You have been invited to %player%'s home"),
    SUCCESS_UNINVITE("Success - home uninvite", "%player% was uninvited to your home"),
    SUCCESS_FCLEAR("Success - home fclear", "Uninvited all friends from home"),
    SUCCESS_WARP_SET("Success - warp set", "Warp %warp% set"),
    SUCCESS_WARP_UPDATE("Success - warp update", "Warp %warp% updated"),
    SUCCESS_WARP_DEL("Success - warp delete", "Warp %warp% deleted"),
    SUCCESS_VORTEX_SET("Success - vortex set", "Vortex %warp% set"),
    SUCCESS_VORTEX_UPDATE("Success - vortex update", "vortex %warp% updated"),
    SUCCESS_VORTEX_DEL("Success - vortex delete", "vortex %warp% deleted"),
    SUCCESS_GLOBAL_SET("Success - global set", "%warp% point set"),
    SUCCESS_GLOBAL_DEL("Success - global delete", "%warp% point deleted"),
    SUCCESS_DENY_TPA("Success - deny tpa", "request denied"),
    SUCCESS_ITEMS_SENT("Success - items sent", "items sent to %player%"),

    CONSOLE_DETECT("Console - plugin detected", "%plugin% detected"),
    CONSOLE_FILE_ERROR("Console - file error", "Error saving %file%"),

    MISC_FORMAT("Misc - format", "Format is %format%"),
    MISC_TELEPORTING("Misc - teleporting", "Teleportation in progress."),
    MISC_TP_FOCUS("Misc - tp focus", "Maintain focus for %seconds% seconds."),
    MISC_TP_CANCELLED("Misc - tp cancelled", "Teleport cancelled."),
    MISC_ONE_USE("Misc - one use", "1 use only - click to activate"),

    WARN_REQUEST_COOLDOWN("Warn - request cooldown","Please wait another %seconds% seconds before sending another request."),
    WARN_INVALID_LOCATION("Warn - invalid location", "The requested location could not be located."),
    WARN_TELEPORT_COOLDOWN("Warn - teleport cooldown", "Please wait another %seconds% seconds before teleporting again."),
    WARN_ADD_SELF("Warn - add self", "You can't invite yourself!"),
    WARN_REMOVE_SELF("Warn - remove self", "You can't uninvite yourself!"),
    WARN_PLAYER_NOT_FOUND("Warn - player not found", "Player %player% not found"),
    WARN_ALREADY_FRIEND("Warn - already friend", "%player% is already invited to your home"),
    WARN_NOT_FRIEND("Warn - not friend", "%player% is not invited to your home"),
    WARN_NOT_HOME("Warn - not home", "This command is for teleporting to friend's homes"),
    WARN_NOT_INVITED("Warn - not invited", "You are not invited to %player%'s home"),
    WARN_HOME_UNSET("Warn - Home unset", "home not set"),
    WARN_FRIEND_HOME_UNSET("Warn - friend home unset", "%player% has not set a home"),
    WARN_WARP_LIMIT("Warn - warp limit", "Warp limit reached"),
    WARN_WARP_NOT_FOUND("Warn - warp not found", "%warp% not found"),
    WARN_VORTEX_LIMIT("Warn - vortex limit", "Vortex limit reached"),
    WARN_VORTEX_NOT_SERVER("Warn - vortex not server", "%warp% belongs to a player"),
    WARN_VORTEX_NOT_FOUND("Warn - vortex not found", "%warp% not found"),
    WARN_VORTEX_NOT_OWNER("Warn - vortex not owner", "You are not the owner of %warp%"),
    WARN_GLOBAL_NOT_SET("Warn - global not set", "%warp% point not set"),
    WARN_NOT_ALLOWED("Warn - not allowed", "You do not have permission for this command"),
    WARN_MAX_WARPS("Warn - max warps", "You already have the maximum number of warps"),
    WARN_MAX_VORTEXES("Warn - max vortexes", "You already have the maximum number of vortexes"),
    WARN_TPA_SELF("Warn - tpa self", "You are already at your current location"),
    WARN_NO_PENDING("Warn - no pending tpa", "You do not have any pending teleport requests"),
    WARN_NOT_ONLINE("Warn - requester not online", "Requester is no longer online"),
    WARN_UNKNOWN_TPA("Warn - request not recognized", "Request type not recognized"),

    ITEM_WARPSTONE("Item - warp stone", "Warp Stone"),
    ITEM_VORTEXSTONE("Item - vortex stone", "Vortex Stone"),

    LORE_WARPSTONE("Lore - warp stone", "It appears unstable"),
    LORE_VORTEXSTONE("Lore - vortex stone", "It vibrates unpredictably"),

    ;

    private MessageSetting messageSetting;

    Message(String messageLabel, String messageDefault) {
        this.messageSetting = new MessageSetting(messageLabel, messageDefault);
    }

    public MessageSetting getMessageSetting() {
        return messageSetting;
    }

    @Override
    public String toString() {
        return get();
    }

    private String get() {
        return ChatColor.translateAlternateColorCodes('&', messageSetting.getMessage());
    }

    public String fromPlayer(String playerName) {
        return get().replaceAll("%player%", playerName);
    }

    public String fromFile(String file) {
        return get().replaceAll("%fiel%", file);
    }

    public String fromPlugin(String plugin) {
        return get().replaceAll("%plugin%", plugin);
    }

    public String fromWarp(String warpName) {
        return get().replaceAll("%warp%", warpName);
    }

    public String fromFormat(String format) {
        return get().replaceAll("%format%", format);
    }

    public String fromSeconds(String seconds) {
        return get().replaceAll("%seconds%", seconds);
    }


}
