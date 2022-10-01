package fontys.s3.Carspacebackend.converters;


import fontys.s3.Carspacebackend.domain.Comment;
import fontys.s3.Carspacebackend.persistence.Entity.CommentEntity;


public class CommentConverter {
    private CommentConverter(){

    }

    public static Comment convertToPOJO(CommentEntity c){
        return Comment.builder().id(c.getId())
                .text(c.getText())
                .createdOn(c.getCreatedOn())
                .build();
    }
    public static CommentEntity convertToEntity(Comment c){
        if(c == null){
            return null;
        }
        return CommentEntity.builder().id(c.getId())
                .text(c.getText())
                .createdOn(c.getCreatedOn())
                .build();
    }
}
