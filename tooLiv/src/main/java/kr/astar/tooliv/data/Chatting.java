package kr.astar.tooliv.data;

public record Chatting(String id, String nickName, String comment) {

    @Override
    public String comment() {
        if (comment == null) return "";
        return comment;
    }

    @Override
    public String toString() {
        return "Chatting{" +
                "id='" + id + '\'' +
                ", nickName='" + nickName + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chatting chatting = (Chatting) o;

        if (!id.equals(chatting.id)) return false;
        if (!nickName.equals(chatting.nickName)) return false;
        return comment.equals(chatting.comment);
    }

}

