package org.bundestagsbot.embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.Assert;
import org.junit.Test;

public class SuccessLogEmbedTest {
    @Test
    public void EmbedBuildTest() {
        SuccessLogEmbed successLogEmbed = new SuccessLogEmbed();
        MessageEmbed messageEmbed = successLogEmbed.build();
        Assert.assertNotNull(messageEmbed);
    }
}