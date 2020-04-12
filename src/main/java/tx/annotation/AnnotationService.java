package tx.annotation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AnnotationService {
    public void transfer() {

    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public void query() {

    }
}
