package coin.banggeul.auth.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum S3ErrorCode {

    IMAGE_UPLOAD_FAILED("이미지 업로드에 실패했습니다."),
    IMAGE_FILE_NAME_NOT_MATCHED("해당 파일과 일치하는 이름이 없습니다.");

    private String defaultMessage;
}
