package org.bundestagsbot.embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.Assert;
import org.junit.Test;

public class ErrorLogEmbedTest {
    @Test
    public void EmbedBuildTest() {
        ErrorLogEmbed errorLogEmbed = new ErrorLogEmbed();
        MessageEmbed messageEmbed = errorLogEmbed.build();
        Assert.assertNotNull(messageEmbed);
    }
}