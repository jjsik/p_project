package pproject.stylelobo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pproject.stylelobo.domain.dto.PersonalReqDto;
import pproject.stylelobo.domain.dto.StatusDTO;
import pproject.stylelobo.domain.dto.personalColorResponseDto;
import pproject.stylelobo.domain.table.Personal_Color;
import pproject.stylelobo.domain.table.Users;
import pproject.stylelobo.services.MyStyleSavedService;
import pproject.stylelobo.services.PersonalColorServices;
import pproject.stylelobo.services.UsersService;
import pproject.stylelobo.status.DefaultRes;
import pproject.stylelobo.status.ResponseMessage;
import pproject.stylelobo.status.StatusCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/api/personalColor")
public class PersonalColorConsultingController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private PersonalColorServices personalColorServices;
    @Autowired
    private MyStyleSavedService myStyleSavedService;


    @PostMapping("/analysis")
    public ResponseEntity<personalColorResponseDto>  personalColorAnalysis(
            //@RequestBody PersonalReqDto reqDto , HttpSession session) throws IOException {
            @RequestParam("image") MultipartFile file , HttpSession session) throws IOException {

        boolean isLogin = Optional.ofNullable((Boolean) session.getAttribute("isLogin")).orElse(false);

        if(isLogin){
            String pythonFilePath = "src\\main\\python\\main2.py";

            String userName = (String) session.getAttribute("username");

            Users user = usersService.userFindByUserName(userName);
            String nickName = user.getNickName();

            File tempFile = File.createTempFile("image_", ".jpg");
            file.transferTo(tempFile);

            System.out.println("tempFile.getAbsolutePath() = " + tempFile.getAbsolutePath());
            
            //데이터 받아오는 로직
            String colorType = "";
            try {
                // Python 파일 실행
                ProcessBuilder proBuilder = new ProcessBuilder("python", pythonFilePath, tempFile.getAbsolutePath());
                proBuilder.redirectErrorStream(true);
                Process process = proBuilder.start();

                // Python 출력 읽기
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
                StringBuilder result = new StringBuilder();
                String line;


                while ((line = bufferReader.readLine()) != null) {
                    System.out.println("line = " + line);
                    result.append(line); // 출력 내용 콘솔에 표시
                    colorType = line;
                }

                bufferReader.close();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            if (tempFile.exists()) {
                tempFile.delete();
            }

            String recommendation = "";

            if(colorType.equals("spring")){
                recommendation += "# 아이보리 계열 # 쉬폰 or 폴리스 소재 \n #밝은 컬러 #화사한 느낌 \n # 페미닌 & 러블리 스타일";
            } else if (colorType.equals("summer")) {
                recommendation += "# 연한 회색 or 소라색 계열 # 쉬폰 or 레이스 소재 \n #차가운 컬러 #화사한 느낌 \n # 페미닌 & 캐주얼 스타일";
            } else if (colorType.equals("fall")) {
                recommendation += "#갈색 계열 #가죽소재 \n #짙은 컬러  #진중한 이미지 \n #에스닉 & 보헤미안 스타일";
            }
            else if (colorType.equals("winter")){
                recommendation += "# 비비드한 색상 계열 # 빳빡한 시스루 or 스웨이드 소재 \n #화이트 & 블랙 #차갑고 모던한 느낌 \n # 오피스 & 올블랙 스타일";
            }
            else{
                String resMsg = ResponseMessage.ANALYSIS_FAIL;
                return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, resMsg, null), HttpStatus.OK);
            }

            personalColorResponseDto responseDto = new personalColorResponseDto(colorType, nickName, recommendation, user.getGender());

            String resMsg = ResponseMessage.ANALYSIS_SUCCESS;

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMsg, responseDto), HttpStatus.OK);
        }
        else{

            StatusDTO dto = new StatusDTO();

            dto.setMessage("로그인 필요");
            String resMsg = ResponseMessage.LOGIN_FAIL;

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, resMsg, dto), HttpStatus.OK);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<StatusDTO> save(@RequestParam("image") MultipartFile file, @RequestParam("color") String colorType,
                                          HttpSession session) throws IOException {

        boolean isLogin = Optional.ofNullable((Boolean) session.getAttribute("isLogin")).orElse(false);
        StatusDTO dto = new StatusDTO();

        if(isLogin){
            String userName = (String) session.getAttribute("username");

            Users user = usersService.userFindByUserName(userName);

            byte[] faceImage = file.getBytes();

            String recommendation = "";

            if(colorType.equals("spring")){
                recommendation += "# 아이보리 계열 # 쉬폰 or 폴리스 소재 \n #밝은 컬러 #화사한 느낌 \n # 페미닌 & 러블리 스타일";
            } else if (colorType.equals("summer")) {
                recommendation += "# 연한 회색 or 소라색 계열 # 쉬폰 or 레이스 소재 \n #차가운 컬러 #화사한 느낌 \n # 페미닌 & 캐주얼 스타일";
            } else if (colorType.equals("fall")) {
                recommendation += "#갈색 계열 #가죽소재 \n #짙은 컬러  #진중한 이미지 \n #에스닉 & 보헤미안 스타일";
            }
            else if (colorType.equals("winter")){
                recommendation += "# 비비드한 색상 계열 # 빳빡한 시스루 or 스웨이드 소재 \n #화이트 & 블랙 #차갑고 모던한 느낌 \n # 오피스 & 올블랙 스타일";
            }


            //퍼스널 컬러 DB에 저장
            Personal_Color personalColorResult = personalColorServices.savePersonalColorResult(colorType, recommendation, faceImage, user);

            //MyStyle에 저장
            myStyleSavedService.saveMyStyle(user, personalColorResult, null);

            dto.setMessage("저장 성공.");

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SAVE_SUCCESS, dto), HttpStatus.OK);
        }
        else{
            dto.setMessage("로그인 필요.");
            return new ResponseEntity(DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.SAVE_FAIL, dto), HttpStatus.OK);
        }

    }
}
