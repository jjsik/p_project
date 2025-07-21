package pproject.stylelobo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pproject.stylelobo.domain.Gender;
import pproject.stylelobo.domain.dto.*;
import pproject.stylelobo.domain.table.FavoriteFashionResults;
import pproject.stylelobo.domain.table.Personal_Color;
import pproject.stylelobo.domain.table.Users;
import pproject.stylelobo.services.FashionImageService;
import pproject.stylelobo.services.MyStyleSavedService;
import pproject.stylelobo.services.UsersService;
import pproject.stylelobo.status.DefaultRes;
import pproject.stylelobo.status.ResponseMessage;
import pproject.stylelobo.status.StatusCode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fashion")
public class FashionImageController {


    @Autowired
    private FashionImageService fashionImageService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private MyStyleSavedService myStyleSavedService;


    @PostMapping("/image")
    public ResponseEntity<List<FashionResDto>> FashionType(@RequestBody FashionTypeDTO fas, HttpSession session)
    {

        String userName = (String) session.getAttribute("username");
        Users user = usersService.userFindByUserName(userName);

        Gender gender = user.getGender();

        if(fas.getType() != null && fas.getText() != null)
        {
            List<ImageResDTO> res = new ArrayList<>();

            AiFashionDTO AIdto = new AiFashionDTO(
                    "성별: " + gender +
                            ", 체형: " + fas.getSelectedBody() +
                            ", 얼굴형: " + fas.getSelectedFace() +
                            ", 스타일: " + fas.getType() +
                            ". 스타일 유형 '" + fas.getType() + "'에 중점을 두고, " +
                            "추가 설명: '" + fas.getText() + "'에 특별히 신경을 써서 디자인하세요. " +
                            "지정된 스타일을 강조하는 세 개의 마네킹을 보여주세요. 각 마네킹은 체형과 얼굴형에 맞게 조정된 해당 스타일을 표현해야 합니다. " +
                            "각 마네킹은 동일한 스타일의 세련되고 독특한 변형을 나타냅니다. " +
                            "스타일 정의:\n" +
                            "- 캐주얼: 편안하고 일상적인 스타일로 기본적인 티셔츠와 청바지 같은 간단한 조합.\n" +
                            "- 스트릿: 힙합, 그래피티, 스트릿웨어에서 영향을 받은 자유롭고 개성 있는 스타일.\n" +
                            "- 빈티지: 과거의 패션에서 영감을 받은 레트로한 스타일.\n" +
                            "- 놈코어: 특별히 튀지 않고 기본 아이템을 활용한 단순하고 편안한 스타일.\n" +
                            "- 클래식: 정장이나 코트 같은 고급스럽고 세련된 스타일.\n" +
                            "- 페미닌: 여성스러운 느낌을 강조한 레이스, 플라워 패턴, 부드러운 실루엣의 스타일.\n" +
                            "- 미니멀: 단순한 디자인과 중립적인 색상을 강조한 간결하고 깔끔한 스타일.\n" +
                            "- 맥시멀: 화려하고 대담한 컬러, 패턴, 액세서리를 강조한 스타일.\n" +
                            "- 스포티: 활동성과 편안함을 강조한 스포츠웨어 스타일.\n" +
                            "- 에스닉: 특정 문화나 전통에서 영감을 받은 독특한 패턴과 소재를 사용하는 스타일.\n" +
                            "- 레이어드: 여러 겹의 옷을 겹쳐 입어 스타일링을 완성하는 기법.\n" +
                            "- 댄디: 세련된 남성 정장 스타일로 깔끔하고 고급스러움을 강조.\n" +
                            "- 모던: 현대적이고 세련된 디자인으로 최신 트렌드를 반영한 스타일.\n" +
                            "- 아방가르드: 실험적이고 예술적인 요소가 돋보이는 독창적인 스타일.\n" +
                            "- 아메카지: 미국 캐주얼과 일본 패션의 결합으로 편안하면서도 개성 있는 스타일.(카키색이 주로 많이 들어감)\n\n" +
                            "선택된 스타일 '" + fas.getType() + "'에 대한 디자인은 위 정의를 기반으로 합니다. " +
                            "선택된 색상과 디자인 세부 사항이 '" + fas.getText() + "'에 제공된 설명과 일치하도록 보장하세요. " +
                            "마네킹은 액세서리, 패턴, 레이어링의 미묘한 차이를 통해 변화를 보여주되, 설명된 스타일에 충실해야 합니다. " +
                            "마네킹과 의상에 초점을 맞출 수 있도록 배경은 중립적이고 최소한으로 유지하세요.",
                    1
            );




            ImageResDTO imageResDTO = fashionImageService.FashionType(AIdto, fas);

            res.add(imageResDTO);

            return new ResponseEntity(DefaultRes.res(StatusCode.OK,ResponseMessage.IMAGE_SUCCESS, res), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(DefaultRes.res(StatusCode.NO_CONTENT, null), HttpStatus.OK);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<StatusDTO> save(@RequestBody FashionTypeDTO fas, HttpSession session) throws IOException {

        boolean isLogin = Optional.ofNullable((Boolean) session.getAttribute("isLogin")).orElse(false);
        StatusDTO dto = new StatusDTO();

        if(isLogin){
            String userName = (String) session.getAttribute("username");

            Users user = usersService.userFindByUserName(userName);

            URL url = new URL(fas.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            byte[] imageBytes = {};

            // 응답 코드 확인
            if (connection.getResponseCode() == 200) {
                // 이미지 데이터를 InputStream으로 읽기
                InputStream inputStream = connection.getInputStream();

                // InputStream을 byte[]로 변환
                imageBytes = inputStream.readAllBytes();

                // 스트림 닫기
                inputStream.close();
            }
            else {
                System.out.println("Failed to download image. HTTP response code: " + connection.getResponseCode());
            }

            System.out.println("Image bytes length: " + imageBytes.length);

            //DB에 저장
            FavoriteFashionResults favoriteFashionResult = fashionImageService.saveFashionImage(fas.getType(), fas.getText(),
                    imageBytes, user, fas.getSelectedFace(), fas.getSelectedBody());
            //MyStyle DB에 저장
            myStyleSavedService.saveMyStyle(user, null, favoriteFashionResult);

            dto.setMessage("저장 성공.");

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SAVE_SUCCESS, dto), HttpStatus.OK);
        }
        else{
            dto.setMessage("로그인 필요.");
            return new ResponseEntity(DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.SAVE_FAIL, dto), HttpStatus.OK);
        }

    }
}
