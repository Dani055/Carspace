package fontys.s3.carspacebackend.converter;

import fontys.s3.carspacebackend.persistence.entity.converters.ImageConverter;
import fontys.s3.carspacebackend.domain.Image;
import fontys.s3.carspacebackend.persistence.entity.ImageEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ImageConverterTest {

    @Test
    void testConvertToPOJO(){
        ImageEntity entity = ImageEntity.builder().id(1L).imgUrl("test").build();

        Image pojo = ImageConverter.convertToPOJO(entity);

        assertEquals(1L, pojo.getId());
        assertEquals("test", pojo.getImgUrl());
    }
    @Test
    void testConvertToPOJO_null(){
        ImageEntity entity = null;

        Image pojo = ImageConverter.convertToPOJO(entity);

        assertNull(pojo);
    }

    @Test
    void testConvertToEntity(){
        Image pojo = Image.builder().id(1L).imgUrl("test").build();

        ImageEntity entity = ImageConverter.convertToEntity(pojo);

        assertEquals(1L, entity.getId());
        assertEquals("test", entity.getImgUrl());
    }
    @Test
    void testConvertToEntity_null(){
        Image pojo = null;

        ImageEntity entity = ImageConverter.convertToEntity(pojo);

        assertNull(entity);
    }
}
