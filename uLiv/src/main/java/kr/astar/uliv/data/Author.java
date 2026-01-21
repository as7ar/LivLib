package kr.astar.uliv.data;

import com.google.api.services.youtube.model.LiveChatMessage;

public record Author(String name, LiveChatMessage message) {}
