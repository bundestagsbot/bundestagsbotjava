package org.bundestagsbot.embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.Assert;
import org.junit.Test;

public class NeutralLogEmbedTest {
    @Test
    public void EmbedBuildTest() {
        NeutralLogEmbed neutralLogEmbed = new NeutralLogEmbed();
        MessageEmbed messageEmbed = neutralLogEmbed.build();
        Assert.assertNotNull(messageEmbed);
    }
}