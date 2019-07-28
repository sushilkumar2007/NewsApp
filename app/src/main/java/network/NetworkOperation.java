package network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

public class NetworkOperation {

    private Context context;
    private int method;
    private Response.Listener<JSONObject> listener;
    private Response.ErrorListener errorListener;
    private JSONObject jsonObject;
    private String url;
    private Map<String, String> params;
    private NetworkOperation(Context context) {
        this.context = context;
    }
    public void execute() {
     JsonObjectRequest request = new JsonObjectRequest(
                method, url, jsonObject,
                listener,
                errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // something to do here ??

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


    }


    private NetworkOperation(NetworkBuilder builder) {
        this.method = builder.method;
        this.listener = builder.listener;
        this.context = builder.context;
        this.errorListener = builder.errorListener;
        this.jsonObject = builder.jsonObject;
        this.url = builder.url;
        this.params = builder.params;

    }

    public static class NetworkBuilder {

        Context context;

        int method;
        Response.Listener<JSONObject> listener;
        Response.ErrorListener errorListener;
        JSONObject jsonObject;
        String url;
        Map<String, String> params;

        public NetworkBuilder setParams(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public NetworkOperation build() {
            return new NetworkOperation(this);
        }

        public NetworkBuilder setContext(Context context) {
            this.context = context;
            return this;
        }

        public NetworkBuilder setMethod(int method) {
            this.method = method;
            return this;
        }

        public NetworkBuilder setListener(Response.Listener<JSONObject> listener) {
            this.listener = listener;
            return this;

        }

        public NetworkBuilder setErrorListener(Response.ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        public NetworkBuilder setJsonObject(JSONObject jsonObject) {
            this.jsonObject = jsonObject;
            return this;
        }

        public NetworkBuilder setUrl(String url) {
            this.url = url;
            return this;
        }
    }


}
