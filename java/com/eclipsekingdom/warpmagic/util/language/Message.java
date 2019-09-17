package com.eclipsekingdom.warpmagic.util.language;

public enum  Message {
    HOME_UNSET( "Home unset", "§chome not set"),
    HOME_SET("Home set", "§ahome set"),
    HOME_UPDATE("Home update", "§ahome updated"),
    HOME_DELETE("Home delete", "§ahome deleted"),
    HOME_INVITE("Home invite", "§2%p §awas invited to your home"),
    HOME_INVITATION("Home invitation", "§aYou have been invited to §2%p§a's home"),

    INVALID_WORLD("Invalid - world", "§cWarp points can not be set in this world"),
    INVALID_CHARACTERS("Invalid - special characters", "§cNames must consist of only a-z, A-Z, 0-9, _, and -"),
    INVALID_TOO_LONG("Invalid - too long", "§cNames must be 20 characters or less"),
    INVALID_RESERVED_WORD("Invalid - reserved word", "§cThe name you selected is reserved by WarpMagic"),

    SUGGEST_FHOME("Suggest - fhome", "§aUse §7/fhome %p §ato teleport there"),
    SUGGEST_HOME("Suggest - home", "§aUse §7/home §ato teleport home"),
    SUGGEST_SET_HOME("Suggest - set home", "§cUse §7/home set §cto set a home"),
    SUGGEST_WARP_UPDATE("Suggest - warp update", "§aUse §7/warp update [warp] §ato update a warp"),
    SUGGEST_WARP_DEL("Suggest - warp del", "§cUse §7/warp del [warp] §cto delete a warp"),
    SUGGEST_VORTEX_UPDATE("Suggest - vortex update", "§cUse §7/vortex update [vortex] §cto update a vortex"),
    SUGGEST_VORTEX_DEL("Suggest - vortex del", "§cUse §7/vortex del [vortex] §cto delete a vortex"),
    SUGGEST_WE("Suggest - warp effect", "§7Use /we to equip an effect"),

    FRIEND_LIST_TITLE("Friend list title","§aFriends:"),
    FHOME_LIST_TITLE("Fhome list title", "§aFriend Homes:"),

    SUCCESS_UNINVITE("Success - home uninvite","§2%p §awas uninvited to your home"),
    SUCCESS_FCLEAR("Success - home fclear", "§aUninvited all friends from home"),
    SUCCESS_WARP_SET("Success - warp set", "§aWarp §2%w §aset"),
    SUCCESS_WARP_UPDATE("Success - warp update", "§aWarp §2%w §aupdated"),
    SUCCESS_WARP_DEL("Success - warp delete", "§aWarp §2%w §awas deleted"),
    SUCCESS_VORTEX_SET("Success - vortex set", "§aVortex §2%w §aset"),
    SUCCESS_VORTEX_UPDATE("Success - vortex update", "§avortex §2%w §aupdated"),
    SUCCESS_VORTEX_DEL("Success - vortex delete", "§avortex §2%w §awas deleted"),
    SUCCESS_GLOBAL_SET("Success - global set", "§a%w point set"),
    SUCCESS_GLOBAL_DEL("Success - global delete", "§a%w point deleted"),
    SUCCESS_EFFECT_UNLOCK("Success - effect unlock", "§aYou unlocked §2%e"),
    SUCCESS_DENY_TPA("Success - deny tpa", "§arequest denied"),

    FORMAT_HOME_INVITE("Format - home invite", "§cFormat is §7/home invite [player]"),
    FORMAT_HOME_UNINVITE("Format - home uninvite","§cFormat is §7/home uninvite [player]"),
    FORMAT_FHOME("Format - fhome","§cFormat is §7/fhome [player]"),
    FORMAT_WARP_SET("Format - warp set","§cFormat is §7/warp set [warp]"),
    FORMAT_WARP_UPDATE("Format - warp update","§cFormat is §7/warp update [warp]"),
    FORMAT_WARP_DEL("Format - warp delete","§cFormat is §7/warp del [warp]"),
    FORMAT_WARP("Format - warp","§cFormat is §7/warp [warp]"),
    FORMAT_VORTEX_SET("Format - vortex set","§cFormat is §7/vortex set [vortex]"),
    FORMAT_VORTEX_UPDATE("Format - vortex update","§cFormat is §7/vortex update [vortex]"),
    FORMAT_VORTEX_DEL("Format - vortex delete","§cFormat is §7/vortex del [vortex]"),
    FORMAT_VORTEX("Format - vortex","§cFormat is §7/vortex [vortex]"),
    FORMAT_TPA("Format - tpa","§cFormat is §7/tpa [player]"),
    FORMAT_TPA_HERE("Format - tpahere","§cFormat is §7/tpahere [player]"),
    FORMAT_EFFECT_STONE("Format - effectstone","§cFormat is §7/effectstone [effect]"),
    
    ERROR_ADD_SELF("Error - add self", "§cYou can't invite yourself!"),
    ERROR_REMOVE_SELF("Error - remove self", "§cYou can't uninvite yourself!"),
    ERROR_PLAYER_NOT_FOUND("Error - player not found", "§cPlayer §7%p §cnot found"),
    ERROR_ALREADY_FRIEND("Error - already friend", "§7%p §cis already invited to your home"),
    ERROR_NOT_FRIEND("Error - not friend", "§7%p §cis not invited to your home"),
    ERROR_NOT_HOME("Error - not home", "§cThis command is for teleporting to friend's homes"),
    ERROR_NOT_INVITED("Error - not invited", "§cYou are not invited to §7%p§c's home"),
    ERROR_FRIEND_HOME_UNSET("Error - friend home unset", "§7%p §chas not set a home"),
    ERROR_WARP_LIMIT("Error - warp limit", "§cWarp limit reached"),
    ERROR_WARP_ALREADY_SET("Error - warp already set", "§cWarp §7%w §calready set"),
    ERROR_WARP_NOT_FOUND("Error - warp not found", "§7%w §cnot found"),
    ERROR_VORTEX_LIMIT("Error - vortex limit", "§cVortex limit reached"),
    ERROR_VORTEX_ALREADY_SET("Error - vortex already set", "§cVortex §7%w §calready set"),
    ERROR_VORTEX_NOT_FOUND("Error - vortex not found", "§7%w §cnot found"),
    ERROR_VORTEX_NOT_OWNER("Error - vortex not owner", "§cYou are not the owner of §7%w"),
    ERROR_GLOBAL_NOT_SET("Error - global not set", "§c%w point not set"),
    ERROR_NOT_ALLOWED("Error - not allowed", "§cYou do not have permission for this command"),
    ERROR_MAX_WARPS("Error - max warps", "§cYou already have the maximum number of warps"),
    ERROR_MAX_VORTEXES("Error - max vortexes", "§cYou already have the maximum number of vortexes"),
    ERROR_EFFECT_UNLOCKED("Error - effect unlocked", "§cYou have already unlocked §7%e"),
    ERROR_TPA_SELF("Error - tpa self", "§cYou are already at your current location"),
    ERROR_NO_PENDING("Error - no pending tpa", "§cYou do not have any pending teleport requests"),
    ERROR_NOT_ONLINE("Error - requester not online", "§cRequester is no longer online"),
    ERROR_UNKNOWN_TPA("Error - request not recognized", "§cRequest type not recognized"),
    ERROR_EFFECT_NOT_FOUND("Error - effect not found", "§cEffect §7%e not §cfound"),

    ;


    private MessageSetting messageSetting;

    Message(String messageSetting, String messageDefault){
        this.messageSetting = new MessageSetting(messageSetting, messageDefault);
    }

    public MessageSetting getMessageSetting() {
        return messageSetting;
    }

    public String get(){
        return messageSetting.getMessage();
    }

    public String getFromPlayer(String playerName){
        return messageSetting.getMessage().replace("%p", playerName);
    }

    public String getFromWarp(String warpName){
        return messageSetting.getMessage().replace("%w", warpName);
    }

    public String getFromEffect(String effectName) {
        return messageSetting.getMessage().replace("%e", effectName);
    }


}
