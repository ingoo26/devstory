package org.project.devstory.file;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/tui-editor")
public class
FileApiController {

    // 파일을 업로드할 디렉터리 경로
    private final String uploadDir = Paths.get("C:", "tui-editor", "upload").toString();

    /**
     * 에디터 이미지 업로드
     * @param image 파일 객체
     * @return 업로드된 파일명
     */
    @PostMapping("/image-upload")
    public String uploadEditorImage(@RequestParam(value="image") final MultipartFile image) {
        if (image.isEmpty()) {
            return "";
        }

        String orgFilename = image.getOriginalFilename();                                         // 원본 파일명
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
        String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);  // 확장자
        String saveFilename = uuid + "." + extension;                                             // 디스크에 저장할 파일명
        String fileFullPath = Paths.get(uploadDir, saveFilename).toString();                      // 디스크에 저장할 파일의 전체 경로

        // uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
        File dir = new File(uploadDir);
        if (dir.exists() == false) {
            dir.mkdirs();
        }

        try {
            // 파일 저장 (write to disk)
            File uploadFile = new File(fileFullPath);
            image.transferTo(uploadFile);
            return saveFilename;

        } catch (IOException e) {
            // 예외 처리는 따로 해주는 게 좋습니다.
            throw new RuntimeException(e);
        }
    }

    /**
     * 디스크에 업로드된 파일을 byte[]로 반환
     * @param filename 디스크에 업로드된 파일명
     * @return image byte array
     */
    @GetMapping(value = "/image-print", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public byte[] printEditorImage(@RequestParam final String filename) {
        // 업로드된 파일의 전체 경로
        String fileFullPath = Paths.get(uploadDir, filename).toString();

        // 파일이 없는 경우 예외 throw
        File uploadedFile = new File(fileFullPath);
        if (uploadedFile.exists() == false) {
            throw new RuntimeException();
        }

        try {
            // 이미지 파일을 byte[]로 변환 후 반환
            byte[] imageBytes = Files.readAllBytes(uploadedFile.toPath());
            return imageBytes;

        } catch (IOException e) {
            // 예외 처리는 따로 해주는 게 좋습니다.
            throw new RuntimeException(e);
        }
    }

    public String makeDir() {

        String path="C:\\Java\\DevStory\\src\\main\\resources\\static\\thumbnail"; //경로

        File file = new File(path);

        if(file.exists()==false) {//파일이 존재하면 true
            file.mkdir(); //폴더생성
        }

        return path;
    }



    @PostMapping("/upload_ok")
    public ResponseEntity<String>uploadOk(@RequestParam MultipartFile image , Model model) {

        //파일명
        String origin = image.getOriginalFilename();
        //브라우저별로 경로가 포함되서 올라오는 경우가 있기에 간단한 처리.
        String filename=origin.substring(origin.lastIndexOf("\\")+1);
        //폴더생성
        String filepath=makeDir();

        //중복파일의 처리
        String uuid=UUID.randomUUID().toString();
        //최종저장경로
        String savename=filepath+"\\"+uuid+"_"+filename;

        //System.out.println(origin);
//        System.out.println(filename);
//        System.out.println(filepath);
//        System.out.println(uuid);
//        System.out.println(savename);


        try {
            File save = new File(savename); //세이브경로
            image.transferTo(save);//업로드 진행

            //썸네일경로
            String thumbsname= filepath+"\\"+uuid+"_thumbs_"+filename;

            //썸네일 생성 (복사할파일위치,썸네일생성경로,가로,세로)
            Thumbnailator.createThumbnail(new File(savename),new File(thumbsname),150, 150);

            model.addAttribute("savename", savename);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(savename);
        return ResponseEntity.ok(savename);
    }
    @PostMapping("/upload_profile")
    public  ResponseEntity<String> uploadprofile(@RequestParam MultipartFile image , Model model) {

        //파일명
        String origin = image.getOriginalFilename();
        //브라우저별로 경로가 포함되서 올라오는 경우가 있기에 간단한 처리.
        String filename=origin.substring(origin.lastIndexOf("\\")+1);
        //폴더생성
        String filepath=makeDir2();

        //중복파일의 처리
        String uuid=UUID.randomUUID().toString();
        //최종저장경로
        String savename=filepath+"\\"+uuid+"_"+filename;

        //System.out.println(origin);
//        System.out.println(filename);
//        System.out.println(filepath);
//        System.out.println(uuid);
//        System.out.println(savename);


        try {
            File save = new File(savename); //세이브경로
            image.transferTo(save);//업로드 진행

            //썸네일경로
            String thumbsname= filepath+"\\"+uuid+"_thumbs_"+filename;

            //썸네일 생성 (복사할파일위치,썸네일생성경로,가로,세로)
            Thumbnailator.createThumbnail(new File(savename),new File(thumbsname),150, 150);

            model.addAttribute("savename", savename);



        } catch (Exception e) {
            e.printStackTrace();
        }


        return ResponseEntity.ok(savename);
    }
    public String makeDir2() {

        String path="C:\\Java\\DevStory\\src\\main\\resources\\static\\profile"; //경로

        File file = new File(path);

        if(file.exists()==false) {//파일이 존재하면 true
            file.mkdir(); //폴더생성
        }

        return path;
    }

    @PostMapping("/upload_profile2")
    public  ResponseEntity<String> uploadprofile2(@RequestParam MultipartFile image , Model model) {

        //파일명
        String origin = image.getOriginalFilename();
        //브라우저별로 경로가 포함되서 올라오는 경우가 있기에 간단한 처리.
        String filename=origin.substring(origin.lastIndexOf("\\")+1);
        //폴더생성
        String filepath=makeDir3();

        //중복파일의 처리
        String uuid=UUID.randomUUID().toString();
        //최종저장경로
        String savename=filepath+"\\"+uuid+"_"+filename;

        //System.out.println(origin);
//        System.out.println(filename);
//        System.out.println(filepath);
//        System.out.println(uuid);
//        System.out.println(savename);


        try {
            File save = new File(savename); //세이브경로
            image.transferTo(save);//업로드 진행

            //썸네일경로
            String thumbsname= filepath+"\\"+uuid+"_thumbs_"+filename;

            //썸네일 생성 (복사할파일위치,썸네일생성경로,가로,세로)
            Thumbnailator.createThumbnail(new File(savename),new File(thumbsname),150, 150);

            model.addAttribute("savename", savename);



        } catch (Exception e) {
            e.printStackTrace();
        }


        return ResponseEntity.ok(savename);
    }

    public String makeDir3() {

        String path="C:\\Java\\DevStory\\src\\main\\resources\\static\\profile"; //경로

        File file = new File(path);

        if(file.exists()==false) {//파일이 존재하면 true
            file.mkdir(); //폴더생성
        }

        return path;
    }

}







