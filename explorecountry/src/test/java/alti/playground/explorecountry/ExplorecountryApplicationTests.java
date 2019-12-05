package alti.playground.explorecountry;

import alti.playground.explorecountry.service.ExploreCountryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExplorecountryApplicationTests {

    @Autowired
    private ExploreCountryService exploreCountryService;

    @Test
    public void checkCountryCount() {
        // given
        Integer count = 3;

        // when
        Integer matchCount = exploreCountryService.getMatchCount("AF", true, false, true);

        // then
        Assert.assertEquals(count,matchCount);
    }

}
