package org.bundestagsbot.embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.Assert;
import org.junit.Test;

public class WarningLogEmbedTest {
    @Test
    public void EmbedBuildTest() {
        WarningLogEmbed warningLogEmbed = new WarningLogEmbed();
        MessageEmbed messageEmbed = warningLogEmbed.build();
        Assert.assertNotNull(messageEmbed);
    }
}