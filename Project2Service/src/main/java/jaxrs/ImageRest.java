package jaxrs;

import impl.RepositoryManagerImpl;
import interfacedef.RepositoryManager;
import model.Image;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Path("image")
public class ImageRest {

    private static final RepositoryManager manager = RepositoryManagerImpl.getInstance();

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataBodyPart bodyPart) {
        JSONObject response = new JSONObject();
        String mime = bodyPart.getMediaType().toString();
        byte[] buff = new byte[8000];
        int bytesRead = 0;
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            while ((bytesRead = uploadedInputStream.read(buff)) != -1) {
                bao.write(buff, 0, bytesRead);
            }
            byte[] fileData = bao.toByteArray();
            Image image = new Image(fileData, mime);
            int imageId = manager.addImage(image);

            if (imageId == -1) {
                response.put("status", 500);
            } else {
                response.put("status", 200);
                response.put("id", imageId);
            }
            bao.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            try {
                response.put("status", 500);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            return response.toString();
        }
        System.out.println(response);
        return response.toString();
    }


    @GET
    @Path("download")
    public Response getImage(@QueryParam("id") int id) {
        Image image = manager.getImage(id);
        System.out.println(image);
        return Response.status(200).entity(image.getContent()).type(image.getMime()).build();
    }

}
