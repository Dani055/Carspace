package fontys.s3.carspacebackend.converters;


import fontys.s3.carspacebackend.domain.Image;

import fontys.s3.carspacebackend.persistence.Entity.ImageEntity;


public class ImageConverter {
    private ImageConverter(){

    }

    public static Image convertToPOJO(ImageEntity i){
        if(i == null){
            return null;
        }
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
