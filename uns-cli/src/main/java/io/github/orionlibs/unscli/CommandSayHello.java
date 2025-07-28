package io.github.orionlibs.unscli;

import org.springframework.shell.command.annotation.Command;

@Command
public class CommandSayHello
{
    @Command(command = "say-hello")
    public String commandSayHello(String arg)
    {
        return "Hello " + arg;
    }
}
