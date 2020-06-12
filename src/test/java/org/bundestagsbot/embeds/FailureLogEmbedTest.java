package org.bundestagsbot.embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.Assert;
import org.junit.Test;

public class FailureLogEmbedTest {
    @Test
    public void EmbedBuildTest() {
        FailureLogEmbed failureLogEmbed = new FailureLogEmbed();
        MessageEmbed messageEmbed = failureLogEmbed.build();
        Assert.assertNotNull(messageEmbed);
    }
}