package x21u025.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/img")
public class ImageController {

	@Value("${app.image.root}")
	private String imageRootPath;

	@GetMapping("/{boardGameName}/{imageName}")
	public ResponseEntity<FileSystemResource> getImage(@PathVariable String boardGameName, @PathVariable String imageName) {
		// 外部の画像ファイルへのパスを指定
		String imagePath = imageRootPath + boardGameName + "\\" + imageName;

		// FileSystemResourceを使用して画像ファイルを読み込み
		FileSystemResource fileSystemResource = new FileSystemResource(imagePath);

		if (fileSystemResource.exists()) {
			// 存在する場合、画像をHTTPレスポンスとして返す
			return ResponseEntity.ok()
					.contentType(MediaType.IMAGE_JPEG) // 画像のMIMEタイプに合わせて設定
					.body(fileSystemResource);
		} else {
			// ファイルが存在しない場合は404エラーを返す
			return ResponseEntity.notFound().build();
		}
	}
}
