package org.bundestagsbot.embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.Assert;
import org.junit.Test;

public class LogEmbedTest {
    @Test
    public void EmbedBuildTest() {
        LogEmbed logEmbed = new LogEmbed();
        MessageEmbed messageEmbed = logEmbed.build();
        Assert.assertNotNull(messageEmbed);
    }
}