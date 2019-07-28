
package entites;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Parcelable
{

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("adx_keywords")
    @Expose
    private String adxKeywords;
    @SerializedName("column")
    @Expose
    private String column;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("asset_id")
    @Expose
    private String assetId;
    @SerializedName("views")
    @Expose
    private Integer views;
//    @SerializedName("des_facet")
//    @Expose
//    private List<String> desFacet = new ArrayList<>();
//    @SerializedName("org_facet")
//    @Expose
//    private List<String> orgFacet = new ArrayList<>();
//    @SerializedName("per_facet")
//    @Expose
//    private List<String> perFacet = new ArrayList<>();
//    @SerializedName("geo_facet")
//    @Expose
//    private List<String> geoFacet = new ArrayList<>();
//    @SerializedName("media")
//    @Expose(serialize = false, deserialize = true)
    private List<Medium> med = new ArrayList<>();
    @SerializedName("uri")
    @Expose
    private String uri;
    public final static Creator<Result> CREATOR = new Creator<Result>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        public Result[] newArray(int size) {
            return (new Result[size]);
        }

    }
    ;

    protected Result(Parcel in) {
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.adxKeywords = ((String) in.readValue((String.class.getClassLoader())));
        this.column = ((String) in.readValue((String.class.getClassLoader())));
        this.section = ((String) in.readValue((String.class.getClassLoader())));
        this.byline = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this._abstract = ((String) in.readValue((String.class.getClassLoader())));
        this.publishedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.source = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.assetId = ((String) in.readValue((String.class.getClassLoader())));
        this.views = ((Integer) in.readValue((Integer.class.getClassLoader())));
//        in.readList(this.desFacet, (String.class.getClassLoader()));
//        in.readList(this.orgFacet, (String.class.getClassLoader()));
//        in.readList(this.perFacet, (String.class.getClassLoader()));
//        in.readList(this.geoFacet, (String.class.getClassLoader()));
         in.readList(this.med, (Medium.class.getClassLoader()));
        this.uri = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Result() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdxKeywords() {
        return adxKeywords;
    }

    public void setAdxKeywords(String adxKeywords) {
        this.adxKeywords = adxKeywords;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

//    public List<String> getDesFacet() {
//        return desFacet;
//    }
//
//    public void setDesFacet(List<String> desFacet) {
//        this.desFacet = desFacet;
//    }
//
//    public List<String> getOrgFacet() {
//        return orgFacet;
//    }
//
//    public void setOrgFacet(List<String> orgFacet) {
//        this.orgFacet = orgFacet;
//    }
//
//    public List<String> getPerFacet() {
//        return perFacet;
//    }
//
//    public void setPerFacet(List<String> perFacet) {
//        this.perFacet = perFacet;
//    }
//
//    public List<String> getGeoFacet() {
//        return geoFacet;
//    }
//
//    public void setGeoFacet(List<String> geoFacet) {
//        this.geoFacet = geoFacet;
//    }

    public List<Medium> getMedia() {
        return med;
    }

    public void setMedia(List<Medium> media) {
        this.med = media;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(url);
        dest.writeValue(adxKeywords);
        dest.writeValue(column);
        dest.writeValue(section);
        dest.writeValue(byline);
        dest.writeValue(type);
        dest.writeValue(title);
        dest.writeValue(_abstract);
        dest.writeValue(publishedDate);
        dest.writeValue(source);
        dest.writeValue(id);
        dest.writeValue(assetId);
        dest.writeValue(views);
//        dest.writeList(desFacet);
//        dest.writeList(orgFacet);
//        dest.writeList(perFacet);
//        dest.writeList(geoFacet);
        dest.writeList(med);
        dest.writeValue(uri);
    }

    public int describeContents() {
        return  0;
    }

}
