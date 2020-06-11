package org.bundestagsbot.embeds;

import net.dv8tion.jda.api.EmbedBuilder;

import java.time.Instant;

public class LogEmbed extends EmbedBuilder {
    public LogEmbed() {
        this.setTimestamp(Instant.now());
    }
}
