package quarkus.hackfest.twitteringestor.bean;

import org.apache.camel.Exchange;
import org.apache.camel.component.twitter.TwitterConstants;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Named("latestTweetBean")
public class LatestTweetBean {

    public static String CURRENT_GP = "CurrentGP";


    public void latestTweet(Exchange exchange){

        List<Status> list = exchange.getIn().getBody(List.class);

        if(list != null && !list.isEmpty()){
            Status status = list.get(0);
            String query = "conversation_id:"+ status.getId();

            exchange.getIn().setHeader(TwitterConstants.TWITTER_KEYWORDS, query);
            exchange.getIn().setHeader(CURRENT_GP, getGPname(status));
        }

    }

    public static String getGPname(Status status){
        String gp =  null ;
        for ( HashtagEntity hashtag : status.getHashtagEntities()  ) {
            if( hashtag.getText().contains("GrandPrix") ){
                gp = hashtag.getText();
                break;
            }
        }
        return gp;
    }

}
