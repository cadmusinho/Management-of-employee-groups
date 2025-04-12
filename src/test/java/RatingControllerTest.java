import com.backend.controllers.RatingController;
import com.backend.models.Rating;
import com.backend.services.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {

    @InjectMocks
    private RatingController ratingController;

    @Mock
    private RatingService ratingService;

    @Test
    public void addRating_shouldReturnCreated() {
        Rating rating = new Rating(5L, 1L, "najs");
        ResponseEntity<Rating> response = ratingController.addRating(rating);
        assertEquals(201, response.getStatusCode().value());
    }
}
