package jaygoo.local.server;


import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import jaygoo.local.server.NanoHTTPD.Response.Status;

public class M3U8HttpServer extends NanoHTTPD {

    private NanoHTTPD server;
    private static final int DEFAULT_PORT = 8686;
    private String filesDir = null;

    public M3U8HttpServer() {
        super(DEFAULT_PORT);
    }

    public M3U8HttpServer(int port) {
        super(port);
    }

    public String createLocalHttpUrl(String filePath){
        Uri uri = Uri.parse(filePath);
        M3U8HttpServerLog.d("filePath uri: "+uri);
        String scheme = uri.getScheme();
        M3U8HttpServerLog.d("filePath scheme: "+scheme);
        if (null != scheme) {
            filePath = uri.toString();
        } else {
            filePath = uri.getPath();
        }
        if (filePath != null){
            filesDir = filePath.substring(0, filePath.lastIndexOf("/") + 1);
           return String.format("http://127.0.0.1:%d%s", myPort, filePath);
        }
        return null;
    }

    /**
     * 启动服务
     */
    public void execute() {
        try {
            server = M3U8HttpServer.class.newInstance();
            server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        } catch (IOException ioe) {
            M3U8HttpServerLog.e("M3U8HttpServer 启动服务失败：\n" + ioe);
            System.exit(-1);
        } catch (Exception e) {
            M3U8HttpServerLog.e("M3U8HttpServer 启动服务失败：\n" + e);
            System.exit(-1);
        }
        M3U8HttpServerLog.d("M3U8HttpServer 服务启动成功：\n");
        try {
            System.in.read();
        } catch (Throwable ignored) {
        }
    }


    /**
     * 关闭服务
     */
    public void finish() {
        if(server != null){
            server.stop();
            M3U8HttpServerLog.d("M3U8HttpServer 服务已经关闭：\n");
            server = null;
        }
    }


    @Override
    public Response serve(IHTTPSession session) {

        String url = String.valueOf(session.getUri());

        M3U8HttpServerLog.d("M3U8HttpServer 请求URL：" + url);

        File file = new File(url);

        if(file.exists()){
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return newFixedLengthResponse(Status.NOT_FOUND, "text/html", "文件不存在：" + url);
            }
            // ts文件
            String mimeType = "video/mpeg";
            if(url.contains(".m3u8")){
                // m3u8文件
                mimeType = "video/x-mpegURL";
            }
            return newChunkedResponse(Status.OK, mimeType, fis);
        } else {
            return newFixedLengthResponse(Status.NOT_FOUND, "text/html", "文件不存在：" + url);
        }
    }
}
