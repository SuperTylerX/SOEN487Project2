package jaxrs;

import impl.RepositoryManagerImpl;
import interfacedef.RepositoryManager;
import model.Album;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;


@Path("album")
public class AlbumRest {

    private static final RepositoryManager manager = RepositoryManagerImpl.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Album> getAlbums() {
        return manager.getAlbums();
    }


    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Album> getAlbum(@QueryParam("albumID") int albumID, @QueryParam("isrc") String isrc,
                                     @QueryParam("title") String title, @QueryParam("year") int year,
                                     @QueryParam("artist") String artist) {
        ArrayList<Album> albumArrayList = new ArrayList<>();
        if (albumID > 0) {
            albumArrayList.add(manager.getAlbumByID(albumID));
        } else {
            if (isrc != null && !isrc.isEmpty()) {
                albumArrayList.add(manager.getAlbumByISRC(isrc));
            } else {
                if (title != null && !title.isEmpty()) {
                    return manager.getAlbumByTitle(title);
                } else {
                    if (year > 0) {
                        return manager.getAlbumByYear(year);
                    } else {
                        if (artist != null && !artist.isEmpty()) {
                            return manager.getAlbumByArtist(artist);
                        } else
                            throw new NotFoundException();
                    }
                }
            }
        }
        return albumArrayList;
    }


    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addAlbum(Album album) {
        JSONObject response = new JSONObject();
        try {
            int albumId = manager.addAlbum(album);
            if (albumId == -1) {
                response.put("status", 500);
            } else {
                response.put("status", 200);
                response.put("albumId", albumId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                response.put("status", 500);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            return response.toString();
        }
        return response.toString();
    }


    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateAlbum(Album album) {
        JSONObject response = new JSONObject();
        try {
            boolean flag = manager.updateAlbum(album);
            if (flag) {
                response.put("status", 500);
            } else {
                response.put("status", 200);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                response.put("status", 500);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            return response.toString();
        }
        return response.toString();
    }


    @DELETE
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeAlbum(@QueryParam("albumId") int albumID) {
        JSONObject response = new JSONObject();
        try {
            System.out.println("?????");
            boolean flag = manager.removeAlbum(albumID);
            if (flag) {
                response.put("status", 500);
            } else {
                response.put("status", 200);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                response.put("status", 500);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            return response.toString();
        }
        return response.toString();
    }
}
