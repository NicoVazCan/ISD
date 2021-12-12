package es.udc.ws.app.client.ui;

import es.udc.ws.app.client.service.ClientAppService;
import es.udc.ws.app.client.service.ClientAppServiceFactory;
import es.udc.ws.app.client.service.dto.ClientExcursionDto;
import es.udc.ws.app.client.service.dto.ClientReservaDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class AppServiceClient
{
    private static final String[] E_TYPES = new String[] {"s","s","s","d","n","n"};
    private static final String[] R_TYPES = new String[] {"s","s","n","s","s"};

    public static void main(String[] args)
    {
        if(args.length == 0) printUsageAndExit();

        ClientAppService clientAppService =
                ClientAppServiceFactory.getService();

        switch(args[0])
        {
            // [addExcursion] AppServiceClient -addExc <city> <description> <date> <price> <maxPlaces>
            case "-addExc":
                validateArgs(args,E_TYPES);
                try {
                    Long excursionId = clientAppService.addExcursion(
                            new ClientExcursionDto(args[1], args[2],
                                    LocalDateTime.parse(args[3]),
                                    BigDecimal.valueOf(Double.parseDouble(args[4])),
                                    Integer.parseInt(args[5])));

                    System.out.println("Excursion " + excursionId + " creada con exito");

                }
                catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }

                break;
            // [addReserva]   AppServiceClient -reserve <userEmail> <excursionId> <creditCardNumber> <places>
            case "-reserve":
                validateArgs(args, R_TYPES);
                try {
                    Long reservaId = clientAppService.addReserva(
                            new ClientReservaDto(args[1],
                                    Integer.parseInt(args[4]), args[3],
                                    Long.parseLong(args[2])));

                    System.out.println("Reserva " + reservaId + " creada con exito");
                }
                catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }
                break;

            default:
                printUsageAndExit();
        }
    }

    private static boolean canParse(String value, String type)
    {
        switch(type)
        {
            case "s":
                return true;
            case "d":
                try
                {
                    LocalDateTime.parse(value);
                    return true;
                }
                catch(DateTimeParseException e) { return false; }
            case "n":
                try
                {
                    Double.parseDouble(value);
                    return true;
                }
                catch(NumberFormatException e) { return false; }
            default:
                return false;
        }
    }

    public static void validateArgs(String[] args, String[] expectedTypes) {
        final int argc = expectedTypes.length;

        if(args.length != argc)
            printUsageAndExit();

        for(int i = 0; i < argc; i++)
            if(!canParse(args[i], expectedTypes[i]))
                printUsageAndExit();
    }

    public static void printUsageAndExit()
    {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage()
    {
        System.err.println("Usage:\n" +
                "    [addExcursion] AppServiceClient -addExc <city> <description> <date> <price> <maxPlaces>\n" +
                "    [addReserva]   AppServiceClient -reserve <userEmail> <excursionId> <creditCardNumber> <places>\n");
    }
}