package fontys.s3.Carspacebackend.converters;

import fontys.s3.Carspacebackend.domain.IRole;
import fontys.s3.Carspacebackend.domain.Image;
import fontys.s3.Carspacebackend.domain.User;
import fontys.s3.Carspacebackend.domain.impl.AdminRole;
import fontys.s3.Carspacebackend.domain.impl.UserRole;
import fontys.s3.Carspacebackend.persistence.Entity.ImageEntity;
import fontys.s3.Carspacebackend.persistence.Entity.RoleEntity;
import fontys.s3.Carspacebackend.persistence.Entity.UserEntity;

public class ImageConverter {
    private ImageConverter(){

    }

    public static Image convertToPOJO(ImageEntity i){
        return Image.builder().id(i.getId())
                .imgUrl(i.getImgUrl())
                .build();
    }
    public static ImageEntity convertToEntity(Image i){
        if(i == null){
            return null;
        }
        return ImageEntity.builder().id(i.getId())
                .imgUrl(i.getImgUrl())
                .build();
    }
}
