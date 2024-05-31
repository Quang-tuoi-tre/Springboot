package honhatquang890.gmail.com.lab2.service;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import honhatquang890.gmail.com.lab2.model.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class BookService {
    private List<Book> listBook = new ArrayList<>(Arrays.asList(
            new Book(1, "Đắc nhân tâm", "Dale Carnegie", "Cuốn sách hay bán chạy nhất thế giới", 15, "templatemo_image_01.jpg"),
            new Book(2, "Quẳng gánh lo đi và vui sống", "Dale Carnegie", "Cuốn sách này cũng giúp người đọc hóa giải những khúc mắc về những lo lắng và nỗi buồn của con người trên mọi hành trình. Từ những phân tích, chứng minh logic, tác giả xây dựng động lực để mỗi người vượt qua những thử thách, tiêu cực và phát triển một thái độ tích cực hơn, sống trọn vẹn từng giây từng phút.", 18, "templatemo_image_02.jpg"),
            new Book(3, "Nhà giả kim", "Paulo Coelho", "Nội dung xoay quanh nhân vật với xuất phát điểm hoàn toàn bình thường, nhưng bằng nghị lực và ý chí phi thường, sự kiên định trong tâm trí mà có thể đạt được điều lớn lao", 21, "templatemo_image_03.jpg"),
            new Book(4, "Hạt giống tâm hồn", "nhiều tác giả", "những câu chuyện thành công, lòng dũng cảm, tinh thần cao cả và cách vượt qua thử thách từ đa dạng các lĩnh vực.", 19, "templatemo_image_04.jpg")
    ));
    public List<Book> getAllBooks() {
        return listBook;
    }
    public Optional<Book> getBookById(int id) {
        return listBook.stream().filter(book -> book.getId() == id).findFirst();
    }
    public void add(Book book) {
        listBook.add(book);
    }

}
