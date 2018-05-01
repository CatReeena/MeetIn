package com.shera.android.meetin;

public class CommentsFilters {
    private Position position;
    private Long commentId;
    private Long projectId;

    public CommentsFilters(Position position){
        this.position = position;
    }

    public CommentsFilters(Position position, Long projectId, Long commentId) {
        this.position = position;
        this.commentId = commentId;
        this.projectId = projectId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}
