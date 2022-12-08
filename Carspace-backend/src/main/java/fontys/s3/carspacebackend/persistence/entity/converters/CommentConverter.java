package fontys.s3.carspacebackend.persistence.entity.converters;

import fontys.s3.carspacebackend.domain.Comment;
import fontys.s3.carspacebackend.controller.dto.CommentDTO;

import fontys.s3.carspacebackend.persistence.entity.CommentEntity;


public class CommentConverter {
    private CommentConverter(){

    }

    public static Comment convertToPOJO(CommentEntity c){
        if(c == null){
            return null;
        }
        return Comment.builder().id(c.getId())
                .text(c.getText())
                .createdOn(c.getCreatedOn())
                .creator(UserConverter.convertToPOJO(c.getCreator()))
                .build();
    }
    public static CommentEntity convertToEntity(Comment c){
        if(c == null){
            return null;
        }
        return CommentEntity.builder().id(c.getId())
                .text(c.getText())
                .createdOn(c.getCreatedOn())
                .creator(UserConverter.convertToEntity(c.getCreator()))
                .build();
    }

    public static CommentDTO convertToDTO(Comment c){
        if(c == null){
            return null;
        }
        return CommentDTO.builder().id(c.getId())
                .text(c.getText())
                .createdOn(c.getCreatedOn())
                .creator(UserConverter.convertToDTO(c.getCreator()))
                .build();
    }
}
