package com.saran.tms.enums;

import java.util.HashMap;
import java.util.Map;

public enum TableNames {

    USERS("users"),
    ORGANIZATIONS("organizations"),
    SPORTS("sports"),
    TOURNAMENTS("tournaments"),
    TOURNAMENT_PARTICIPANTS("tournament_participants"),
    TOURNAMENT_TEAMS("tournament_teams"),
    TEAM_MEMBERS("team_members"),
    TOURNAMENT_EVENTS("tournament_events"),
    TOURNAMENT_EVENT_TEAMS("tournament_event_teams"),
    TOURNAMENT_EVENT_PARTICIPANTS("tournament_event_participants");

    private String tableName;
    
    private static Map<String, TableNames> tableMap;

    private void addToMap(String tableName, TableNames tableEnum) {
        if (tableMap == null) tableMap = new HashMap<>();
        tableMap.put(tableName, tableEnum);
    }

    private TableNames(String tableName) {
        this.tableName = tableName;
        addToMap(tableName, this);
    }

    public String getTableName() {
        return this.tableName;
    }

    public static TableNames getEnumValue(String tableName) throws IllegalArgumentException {
        TableNames tableEnum = tableMap.get(tableName);
        if (tableEnum == null) {
            throw new IllegalArgumentException("Table name not found");
        }
        return tableEnum;
    }
}
