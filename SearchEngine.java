import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> list = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "Current list: " + list.toString();
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                list.add(parameters[1]);
                return parameters[1] + " has been added.";
            }
            else if(url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                String toSearch = parameters[1];
                String ret = "";
                for (int i=0; i<list.size(); i++) {
                    if (list.get(i).contains(toSearch)) {
                        ret = ret + list.get(i) + " ";
                    }
                }
                return ret;
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
