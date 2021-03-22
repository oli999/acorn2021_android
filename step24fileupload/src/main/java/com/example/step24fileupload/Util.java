package com.example.step24fileupload;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Util {
    /**
     * This utility class provides an abstraction layer for sending multipart HTTP
     * POST requests to a web server.
     * @author www.codejava.net
     *
     */
    public class MultipartUtility {
        private final String boundary;
        private static final String LINE_FEED = "\r\n";
        private HttpURLConnection httpConn;
        private String charset;
        private OutputStream outputStream;
        private PrintWriter writer;

        /**
         * This constructor initializes a new HTTP POST request with content type
         * is set to multipart/form-data
         * @param requestURL
         * @param charset
         * @throws IOException
         */
        public MultipartUtility(String requestURL, String charset)
                throws IOException {
            this.charset = charset;

            // creates a unique boundary based on time stamp
            boundary = "===" + System.currentTimeMillis() + "===";

            URL url = new URL(requestURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true); // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
            outputStream = httpConn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                    true);
        }

        /**
         * Adds a form field to the request
         * @param name field name
         * @param value field value
         */
        public void addFormField(String name, String value) {
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(
                    LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a upload file section to the request
         * @param fieldName name attribute in <input type="file" name="..." />
         * @param uploadFile a File to be uploaded
         * @throws IOException
         */
        public void addFilePart(String fieldName, File uploadFile)
                throws IOException {
            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append(
                    "Content-Disposition: form-data; name=\"" + fieldName
                            + "\"; filename=\"" + fileName + "\"")
                    .append(LINE_FEED);
            writer.append(
                    "Content-Type: "
                            + URLConnection.guessContentTypeFromName(fileName))
                    .append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();

            writer.append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a header field to the request.
         * @param name - name of the header field
         * @param value - value of the header field
         */
        public void addHeaderField(String name, String value) {
            writer.append(name + ": " + value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Completes the request and receives response from the server.
         * @return a list of Strings as response in case the server returned
         * status OK, otherwise an exception is thrown.
         * @throws IOException
         */
        public List<String> finish() throws IOException {
            List<String> response = new ArrayList<String>();

            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            // checks server's status code first
            int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    response.add(line);
                }
                reader.close();
                httpConn.disconnect();
            } else {
                throw new IOException("Server returned non-OK status: " + status);
            }

            return response;
        }
    }
    //파일 업로드 처리
    public static void sendMultipartRequest
        (int requestId,
         String requestUrl,
         Map<String, String> params,
         String filePath,
         RequestListener listener){
            MultipartRequestTask task=new MultipartRequestTask();
            task.setRequestId(requestId);
            task.setRequestUrl(requestUrl);
            task.setListener(listener);
            task.setFilePath(filePath);
            task.execute(params);
        }

    public static class MultipartRequestTask extends AsyncTask<Map<String, String>, Void, Map<String, Object>>{
        private int requestId;
        private String requestUrl;
        private RequestListener listener;
        private String filePath;
        private String fileParamName;
        //-------------------------------------------
        private final String boundary;
        private static final String LINE_FEED = "\r\n";
        private String charset;

        //생성자
        public MultipartRequestTask(){
            // creates a unique boundary based on time stamp
            boundary = "===" + System.currentTimeMillis() + "===";
            charset="utf-8";
            fileParamName="myFile"; // <input type="file" name="myFile"/>
        }
        public void setRequestId(int requestId) {
            this.requestId = requestId;
        }

        public void setRequestUrl(String requestUrl) {

            this.requestUrl = requestUrl;
        }

        public void setListener(RequestListener listener) {
            this.listener = listener;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        @Override
        protected Map<String, Object> doInBackground(Map<String, String>... params) {
            Map<String, String> param=params[0];
            //서버가 http 요청에 대해서 응답하는 문자열을 누적할 객체
            StringBuilder builder=new StringBuilder();
            HttpURLConnection conn=null;
            InputStreamReader isr=null;
            PrintWriter pw=null;
            OutputStream os=null;
            FileInputStream fis=null;
            BufferedReader br=null;
            //결과값을 담을 Map Type 객체
            Map<String,Object> map=new HashMap<String,Object>();
            try{
                //URL 객체 생성
                URL url=new URL(requestUrl);
                //HttpURLConnection 객체의 참조값 얻어오기
                conn=(HttpURLConnection)url.openConnection();
                if(conn!=null){//연결이 되었다면
                    conn.setConnectTimeout(20000); //응답을 기다리는 최대 대기 시간
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.setUseCaches(false);//케쉬 사용 여부
                    //전송하는 데이터에 맞게 값 설정하기
                    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                    conn.setRequestProperty("User-Agent", "CodeJava Agent");
                    os=conn.getOutputStream();
                    //출력할 스트림 객체 얻어오기
                    pw=new PrintWriter(new OutputStreamWriter(os, charset));
                    //-------------- 전송 파라미터 추가  ------------------
                    if(param!=null){//요청 파리미터가 존재 한다면

                        Set<String> keySet=param.keySet();
                        Iterator<String> it=keySet.iterator();

                        //반복문 돌면서 map 에 담긴 모든 요소를 전송할수 있도록 구성한다.
                        while(it.hasNext()){
                            String key=it.next();
                            pw.append("--" + boundary).append(LINE_FEED);
                            pw.append("Content-Disposition: form-data; name=\"" + key + "\"")
                                    .append(LINE_FEED);
                            pw.append("Content-Type: text/plain; charset=" + charset).append(
                                    LINE_FEED);
                            pw.append(LINE_FEED);
                            pw.append(param.get(key)).append(LINE_FEED);
                            pw.flush();
                        }
                    }
                    //------------- File Field ------------------
                    File file=new File(filePath);
                    String filename=file.getName(); //파일명
                    pw.append("--" + boundary).append(LINE_FEED);
                    pw.append("Content-Disposition: form-data; name=\"" + fileParamName + "\"; filename=\"" + filename + "\"")
                            .append(LINE_FEED);
                    pw.append("Content-Type: " + URLConnection.guessContentTypeFromName(filename))
                            .append(LINE_FEED);
                    pw.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
                    pw.append(LINE_FEED);
                    pw.flush();

                    fis = new FileInputStream(file);
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                    os.flush();
                    pw.append(LINE_FEED);
                    pw.flush();
                    pw.append(LINE_FEED).flush();
                    pw.append("--" + boundary + "--").append(LINE_FEED);
                    pw.flush();
                    //응답 코드를 읽어온다.
                    int responseCode=conn.getResponseCode();
                    //Map 객체에 응답 코드를 담는다.
                    map.put("code", responseCode);
                    if(responseCode==200){//정상 응답이라면...
                        //서버가 출력하는 문자열을 읽어오기 위한 객체
                        isr=new InputStreamReader(conn.getInputStream());
                        br=new BufferedReader(isr);
                        //반복문 돌면서 읽어오기
                        while(true){
                            //한줄씩 읽어들인다.
                            String line=br.readLine();
                            //더이상 읽어올 문자열이 없으면 반복문 탈출
                            if(line==null)break;
                            //읽어온 문자열 누적 시키기
                            builder.append(line);
                        }
                        //출력받은 문자열 전체 얻어내기
                        String str=builder.toString();
                        //Map 객체에 결과 문자열을 담는다.
                        map.put("data", str);
                    }
                }
            }catch(Exception e){//예외가 발생하면
                //에러 정보를 담는다.
                map.put("code",-1);
                map.put("data", e.getMessage());
            }finally {
                try{
                    if(pw!=null)pw.close();
                    if(isr!=null)isr.close();
                    if(br!=null)br.close();
                    if(fis!=null) isr.close();
                    if(os!=null)os.close();
                    if(conn!=null)conn.disconnect();
                }catch(Exception e){}
            }

            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);
            int code=(int)map.get("code");
            if(code!=-1){//성공이라면
                listener.onSuccess(requestId, map);
            }else{//실패 (예외발생)
                listener.onSuccess(requestId, map);
            }
        }
    }


    //키보드 숨기는 메소드
    public static void hideKeyboard(Activity activity){

        InputMethodManager iManager=(InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus()==null)return;
        iManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    //메소드의 인자로 전달되는 View 객체의 포커스 해제하는 메소드
    public static void releaseFocus(View view) {
        ViewParent parent = view.getParent();
        ViewGroup group = null;
        View child = null;
        while (parent != null) {
            if (parent instanceof ViewGroup) {
                group = (ViewGroup) parent;
                for (int i = 0; i < group.getChildCount(); i++) {
                    child = group.getChildAt(i);
                    if(child != view && child.isFocusable())
                        child.requestFocus();
                }
            }
            parent = parent.getParent();
        }
    }

    public static interface RequestListener{
        public void onSuccess(int requestId, Map<String, Object> result);
        public void onFail(int requestId, Map<String, Object> result);
    }
    /*
        1. 사용할때 RequestListener 인터페이스 Type 을 전달한다.
        2. 결과는 RequestListener 객체에 전달된다.
        3. Map<String,Object>  에서 응답 코드는
            "code" 라는 키값으로 200, 404, 500, -1 중에 하나가 리턴되고
             -1 이 리턴되면 Exception 발생으로 실패이다. onFail() 호출
     */
    public static void sendGetRequest(int requestId,
                                      String requestUrl,
                                      Map<String,String> params,
                                      RequestListener listener){
        RequestTask task=new RequestTask();
        task.setRequestId(requestId);
        task.setRequestUrl(requestUrl);
        task.setListener(listener);
        task.execute(params);
    }
    private static class RequestTask extends AsyncTask<Map<String,String>, Void, Map<String,Object>> {
        private int requestId;
        private String requestUrl;
        private RequestListener listener;

        public void setListener(RequestListener listener) {
            this.listener = listener;
        }

        public void setRequestId(int requestId) {
            this.requestId = requestId;
        }
        public void setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
        }
        @Override
        protected Map<String, Object> doInBackground(Map<String, String>... params) {
            String requestUrl=this.requestUrl;
            Map<String, String> param=params[0];
            if(param!=null){//요청 파리미터가 존재 한다면
                //서버에 전송할 데이터를 문자열로 구성하기
                StringBuffer buffer=new StringBuffer();
                Set<String> keySet=param.keySet();
                Iterator<String> it=keySet.iterator();
                boolean isFirst=true;
                //반복문 돌면서 map 에 담긴 모든 요소를 전송할수 있도록 구성한다.
                while(it.hasNext()){
                    String key=it.next();
                    String arg=null;
                    //파라미터가 한글일 경우 깨지지 않도록 하기 위해.
                    String encodedValue=null;
                    try {
                        encodedValue= URLEncoder.encode(param.get(key), "utf-8");
                    } catch (UnsupportedEncodingException e) {}
                    if(isFirst){
                        arg="?"+key+"="+encodedValue;
                        isFirst=false;
                    }else{
                        arg="&"+key+"="+encodedValue;
                    }
                    buffer.append(arg);
                }
                String data=buffer.toString();
                requestUrl +=data;
            }
            //서버가 http 요청에 대해서 응답하는 문자열을 누적할 객체
            StringBuilder builder=new StringBuilder();
            HttpURLConnection conn=null;
            InputStreamReader isr=null;
            BufferedReader br=null;
            //결과값을 담을 Map Type 객체
            Map<String,Object> map=new HashMap<String,Object>();
            try{
                //URL 객체 생성
                URL url=new URL(requestUrl);
                //HttpURLConnection 객체의 참조값 얻어오기
                conn=(HttpURLConnection)url.openConnection();
                if(conn!=null){//연결이 되었다면
                    conn.setConnectTimeout(20000); //응답을 기다리는 최대 대기 시간
                    conn.setRequestMethod("GET");//Default 설정
                    conn.setUseCaches(false);//케쉬 사용 여부
                    //응답 코드를 읽어온다.
                    int responseCode=conn.getResponseCode();
                    //Map 객체에 응답 코드를 담는다.
                    map.put("code", responseCode);
                    if(responseCode==200){//정상 응답이라면...
                        //서버가 출력하는 문자열을 읽어오기 위한 객체
                        isr=new InputStreamReader(conn.getInputStream());
                        br=new BufferedReader(isr);
                        //반복문 돌면서 읽어오기
                        while(true){
                            //한줄씩 읽어들인다.
                            String line=br.readLine();
                            //더이상 읽어올 문자열이 없으면 반복문 탈출
                            if(line==null)break;
                            //읽어온 문자열 누적 시키기
                            builder.append(line);
                        }
                        //출력받은 문자열 전체 얻어내기
                        String str=builder.toString();
                        //아래 코드는 수행 불가
                        //console.setText(str);
                        //Map 객체에 결과 문자열을 담는다.
                        map.put("data", str);
                    }
                }
            }catch(Exception e){//예외가 발생하면
                //에러 정보를 담는다.
                map.put("code",-1);
                map.put("data", e.getMessage());
            }finally {
                try{
                    if(isr!=null)isr.close();
                    if(br!=null)br.close();
                    if(conn!=null)conn.disconnect();
                }catch(Exception e){}
            }
            //결과를 담고 있는 Map 객체를 리턴해준다.
            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);
            int code=(int)map.get("code");
            if(code!=-1){//성공이라면
                listener.onSuccess(requestId, map);
            }else{//실패 (예외발생)
                listener.onFail(requestId, map);
            }
        }
    }
    //POST 방식 REQUEST
    public static void sendPostRequest(int requestId, String requestUrl, Map<String, String> params, RequestListener listener){
        PostRequestTask task=new PostRequestTask();
        task.setRequestId(requestId);
        task.setRequestUrl(requestUrl);
        task.setListener(listener);
        task.execute(params);
    }

    public static class PostRequestTask extends AsyncTask<Map<String, String>, Void, Map<String, Object>>{
        private int requestId;
        private String requestUrl;
        private RequestListener listener;

        public void setListener(RequestListener listener) {
            this.listener = listener;
        }
        public void setRequestId(int requestId) {
            this.requestId = requestId;
        }
        public void setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
        }
        @Override
        protected Map<String, Object> doInBackground(Map<String, String>... params) {
            Map<String, String> param=params[0];
            String queryString="";
            if(param!=null){//요청 파리미터가 존재 한다면
                //서버에 전송할 데이터를 문자열로 구성하기
                StringBuffer buffer=new StringBuffer();
                Set<String> keySet=param.keySet();
                Iterator<String> it=keySet.iterator();
                boolean isFirst=true;
                //반복문 돌면서 map 에 담긴 모든 요소를 전송할수 있도록 구성한다.
                while(it.hasNext()){
                    String key=it.next();
                    String arg=null;
                    if(isFirst){
                        arg=key+"="+param.get(key);
                        isFirst=false;
                    }else{
                        arg="&"+key+"="+param.get(key);
                    }
                    buffer.append(arg);
                }
                queryString=buffer.toString();
            }
            //서버가 http 요청에 대해서 응답하는 문자열을 누적할 객체
            StringBuilder builder=new StringBuilder();
            HttpURLConnection conn=null;
            InputStreamReader isr=null;
            BufferedReader br=null;
            PrintWriter pw=null;
            //결과값을 담을 Map Type 객체
            Map<String,Object> map=new HashMap<String,Object>();
            try{
                //URL 객체 생성
                URL url=new URL(requestUrl);
                //HttpURLConnection 객체의 참조값 얻어오기
                conn=(HttpURLConnection)url.openConnection();
                if(conn!=null){//연결이 되었다면
                    conn.setConnectTimeout(20000); //응답을 기다리는 최대 대기 시간
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setUseCaches(false);//케쉬 사용 여부
                    //전송하는 데이터에 맞게 값 설정하기
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); //폼전송과 동일
                    //출력할 스트림 객체 얻어오기
                    OutputStreamWriter osw=
                            new OutputStreamWriter(conn.getOutputStream());
                    //문자열을 바로 출력하기 위해 osw 객체를 PrintWriter 객체로 감싼다
                    pw=new PrintWriter(osw);
                    //서버로 출력하기
                    pw.write(queryString);
                    pw.flush();

                    //응답 코드를 읽어온다.
                    int responseCode=conn.getResponseCode();
                    //Map 객체에 응답 코드를 담는다.
                    map.put("code", responseCode);
                    if(responseCode==200){//정상 응답이라면...
                        //서버가 출력하는 문자열을 읽어오기 위한 객체
                        isr=new InputStreamReader(conn.getInputStream());
                        br=new BufferedReader(isr);
                        //반복문 돌면서 읽어오기
                        while(true){
                            //한줄씩 읽어들인다.
                            String line=br.readLine();
                            //더이상 읽어올 문자열이 없으면 반복문 탈출
                            if(line==null)break;
                            //읽어온 문자열 누적 시키기
                            builder.append(line);
                        }
                        //출력받은 문자열 전체 얻어내기
                        String str=builder.toString();
                        //아래 코드는 수행 불가
                        //console.setText(str);
                        //Map 객체에 결과 문자열을 담는다.
                        map.put("data", str);
                    }
                }
            }catch(Exception e){//예외가 발생하면
                //에러 정보를 담는다.
                map.put("code",-1);
                map.put("data", e.getMessage());
            }finally {
                try{
                    if(pw!=null)pw.close();
                    if(isr!=null)isr.close();
                    if(br!=null)br.close();
                    if(conn!=null)conn.disconnect();
                }catch(Exception e){}
            }
            //결과를 담고 있는 Map 객체를 리턴해준다.
            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);
            int code=(int)map.get("code");
            if(code!=-1){//성공이라면
                listener.onSuccess(requestId, map);
            }else{//실패 (예외발생)
                listener.onFail(requestId, map);
            }
        }
    }
}
