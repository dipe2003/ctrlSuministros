/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.interfaz.herramientas;

import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.lote.Ingreso;
import com.dperez.inalerlab.suministro.lote.Lote;
import com.dperez.inalerlab.suministro.lote.Salida;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import static web.interfaz.herramientas.TipoLibro.LISTADO_ESTADO_SUMINISTRO;
import static web.interfaz.herramientas.TipoLibro.LISTADO_SUMINISTRO;

/**
 *
 * @author dipe2
 */
public class Excelsea {

    Workbook workbook = new HSSFWorkbook();

    private final CellStyle estiloEncabezadoTabla = workbook.createCellStyle();
    private final CellStyle estiloContenidoTabla = workbook.createCellStyle();
    private final CellStyle estiloContenidoTablaFecha = workbook.createCellStyle();
    private final HSSFFont fuenteEncabezado = ((HSSFWorkbook) workbook).createFont();
    private final HSSFFont fuenteContenido = ((HSSFWorkbook) workbook).createFont();

    // Vigencia
    private final CellStyle estiloContenidoTablaNoVigente = workbook.createCellStyle();
    private final CellStyle estiloContenidoTablaRequiereAtencion = workbook.createCellStyle();

    private final String[] encabezadosListado = new String[]{
        "Tipo",
        "Suministro",
        "Proveedor",
        "Codigo SAP",
        "Stock Minimo",
        "Unidad",
        "Con validacion",
        "En Uso"
    };

    private final String[] encabezadosEstadoSuministros = new String[]{
        "Tipo",
        "Suministro",
        "Proveedor",
        "Ultimo Ingreso",
        "Stock Actual",
        "Unidad",
        "Estado"
    };

    private final String[] encabezadosInfoIngresosSuministros = new String[]{
        "Lote",
        "Fecha Ingreso",
        "Factura",
        "Cantidad",
        "Unidad",
        "Vencimiento",
        "Observaciones"
    };

    private final String[] encabezadosInfoSalidasSuministros = new String[]{
        "Lote",
        "Fecha Salida",
        "Cantidad",
        "Unidad",
        "Vencimiento",
        "Observaciones"
    };

    private final String[] encabezadosInfoMovimientosSuministros = new String[]{
        "Lote",
        "Vencimiento",
        "Cantidad Ingreso",
        "Cantidad Salida",
        "Stock Lote",
        "Unidad"
    };

    public Excelsea() {

        fuenteEncabezado.setFontName("Arial");
        fuenteEncabezado.setFontHeightInPoints((short) 11);
        fuenteEncabezado.setBold(true);

        fuenteContenido.setFontName("Arial");
        fuenteContenido.setFontHeightInPoints((short) 10);
        fuenteContenido.setBold(false);

        estiloContenidoTabla.setWrapText(true);
        estiloContenidoTabla.setAlignment(HorizontalAlignment.LEFT);
        estiloContenidoTabla.setVerticalAlignment(VerticalAlignment.CENTER);
        estiloContenidoTabla.setFont(fuenteContenido);
        estiloContenidoTabla.setBorderBottom(BorderStyle.THIN);
        estiloContenidoTabla.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        estiloContenidoTabla.setShrinkToFit(true);

        estiloContenidoTablaFecha.cloneStyleFrom(estiloContenidoTabla);
        estiloContenidoTablaFecha.setDataFormat((short) 14);

        estiloEncabezadoTabla.cloneStyleFrom(estiloContenidoTabla);
        estiloEncabezadoTabla.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        estiloEncabezadoTabla.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloEncabezadoTabla.setFont(fuenteEncabezado);
        estiloEncabezadoTabla.setBorderBottom(BorderStyle.MEDIUM);

        estiloContenidoTablaNoVigente.cloneStyleFrom(estiloContenidoTabla);
        estiloContenidoTablaNoVigente.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloContenidoTablaNoVigente.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

        estiloContenidoTablaRequiereAtencion.cloneStyleFrom(estiloContenidoTabla);
        estiloContenidoTablaRequiereAtencion.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloContenidoTablaRequiereAtencion.setFillForegroundColor(IndexedColors.RED.getIndex());

    }

    private void CrearHojaLibroExcel(List<Suministro> suministros, TipoLibro tipoDeLibro) {

        Sheet sheet = workbook.createSheet("Suministros");

        switch (tipoDeLibro) {
            case LISTADO_SUMINISTRO -> {
                CrearEncabezadosTabla(sheet, encabezadosListado);
                CrearContenidoTabla(sheet, suministros, false);
            }

            case LISTADO_ESTADO_SUMINISTRO -> {
                CrearEncabezadosTabla(sheet, encabezadosEstadoSuministros);
                CrearContenidoTabla(sheet, suministros, true);
            }

            default -> {

            }
        }

    }

    private void CrearHojaLibroExcelIngresos(Suministro suministro) {

        Sheet sheet = workbook.createSheet("Ingresos");

        CrearEncabezadosTabla(sheet, encabezadosInfoIngresosSuministros);
        CrearContenidoTablaIngresos(sheet, suministro);

    }

    private void CrearHojaLibroExcelSalidas(Suministro suministro) {

        Sheet sheet = workbook.createSheet("Salidas");

        CrearEncabezadosTabla(sheet, encabezadosInfoSalidasSuministros);
        CrearContenidoTablaSalidas(sheet, suministro);

    }

    private void CrearHojaLibroExcelMovimientos(Suministro suministro) {

        Sheet sheet = workbook.createSheet("Movimientos");

        CrearEncabezadosTabla(sheet, encabezadosInfoMovimientosSuministros);
        CrearContenidoTablaMovimientos(sheet, suministro);

    }

    private void ExportarLibroExcel(String tituloArchivo) {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + tituloArchivo + ".xls");
            OutputStream outputStream = response.getOutputStream();

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();

            workbook.write(outStream);
            workbook.close();

            outputStream.write(outStream.toByteArray());
            outputStream.flush();
            outputStream.close();

            context.responseComplete();
        } catch (IOException ex) {
            System.out.println("IO ERROR" + ex.getMessage());
        }
    }

    private void CrearEncabezadosTabla(Sheet hoja, String[] encabezadosDeTabla) {
        Row titulos = hoja.createRow(0);
        Cell celdaTitulo = null;

        for (int i = 0; i < encabezadosDeTabla.length; i++) {
            celdaTitulo = titulos.createCell(i);
            hoja.setColumnWidth(i, 6000);
            celdaTitulo.setCellValue(encabezadosDeTabla[i]);
            celdaTitulo.setCellStyle(estiloEncabezadoTabla);
        }
    }

    private void CrearContenidoTabla(Sheet hoja, List<Suministro> suministros, boolean isEstado) {
        int[] numFila = new int[]{1};
        if (isEstado) {
            for (var a : suministros) {
                AgregarRegistroEstado(a, hoja, numFila);
            }
        } else {
            for (var a : suministros) {
                AgregarRegistro(a, hoja, numFila);
            }
        }
    }

    private void CrearContenidoTablaIngresos(Sheet hoja, Suministro suministro) {
        int[] numFila = new int[]{1};

        for (var lote : suministro.getLotesSuministros()) {
            if (!lote.getIngresosLote().isEmpty()) {
                AgregarRegistroIngreso(lote, hoja, numFila);
            }
        }
    }

    private void CrearContenidoTablaSalidas(Sheet hoja, Suministro suministro) {
        int[] numFila = new int[]{1};

        for (var lote : suministro.getLotesSuministros()) {
            if (!lote.getSalidasLote().isEmpty()) {
                AgregarRegistroSalida(lote, hoja, numFila);
            }
        }
    }

    private void CrearContenidoTablaMovimientos(Sheet hoja, Suministro suministro) {
        int[] numFila = new int[]{1};

        for (var lote : suministro.getLotesSuministros()) {
            if (!lote.getIngresosLote().isEmpty()) {
                AgregarRegistroMovimiento(lote, hoja, numFila);
            }
        }
    }

    private void AgregarRegistro(Suministro suministro, Sheet hoja, int[] numFila) {
        Cell celda = null;
        Row fila = null;
        int[] columnaActual;
        // argg

        columnaActual = new int[]{0};
        fila = hoja.createRow(numFila[0]++);

        celda = getNextCelda(fila, columnaActual);

        switch (suministro.getClass().getSimpleName()) {
            case "ReactivoQuimico" ->
                celda.setCellValue("Reactivo Quimico");
            case "MedioEnsayo" ->
                celda.setCellValue("Medio de Ensayo");
            default ->
                celda.setCellValue(suministro.getClass().getSimpleName());
        }

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(suministro.getNombreSuministro());

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(suministro.getProveedorSuministro().getNombreProveedor());

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(suministro.getCodigoSAPSuministro());

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(String.valueOf(suministro.getStockMinimoSuministro().getCantidadStockMinimo()));

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(suministro.getUnidadSuministro().getNombreUnidad());

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(suministro.isAvisoCambioLote() == true ? "Si" : "No");

        celda = getNextCelda(fila, columnaActual);
        if (suministro.isVigente()) {
            celda.setCellValue("Si");
        } else {
            celda.setCellStyle(estiloContenidoTablaNoVigente);
            celda.setCellValue("No");
        }

    }

    private void AgregarRegistroEstado(Suministro suministro, Sheet hoja, int[] numFila) {
        Cell celda = null;
        Row fila = null;
        int[] columnaActual;
        // argg

        columnaActual = new int[]{0};
        fila = hoja.createRow(numFila[0]++);

        celda = getNextCelda(fila, columnaActual);

        switch (suministro.getClass().getSimpleName()) {
            case "ReactivoQuimico" ->
                celda.setCellValue("Reactivo Quimico");
            case "MedioEnsayo" ->
                celda.setCellValue("Medio de Ensayo");
            default ->
                celda.setCellValue(suministro.getClass().getSimpleName());
        }

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(suministro.getNombreSuministro());

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(suministro.getProveedorSuministro().getNombreProveedor());

        celda = getNextCelda(fila, columnaActual);
        if (suministro.getUltimoIngreso() != null) {
            celda.setCellValue(String.valueOf(suministro.getUltimoIngreso().getCantidadIngreso()));
        } else {
            celda.setCellValue("0");
        }

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(String.valueOf(suministro.getStock()));

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(suministro.getUnidadSuministro().getNombreUnidad());

        // estado
        celda = getNextCelda(fila, columnaActual);
        if (!suministro.getLotesVencidosEnStock().isEmpty() || suministro.isDebajoStockMinimo()) {
            celda.setCellStyle(estiloContenidoTablaRequiereAtencion);
            celda.setCellValue("Requiere Atención");
        } else {
            celda.setCellValue("Ok");
        }
    }

    private void AgregarRegistroIngreso(Lote lote, Sheet hoja, int[] numFila) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy");
        Cell celda = null;
        Row fila = null;
        int[] columnaActual;
        int i = 0;
        // argg
        do {
            columnaActual = new int[]{0};
            fila = hoja.createRow(numFila[0]++);

            celda = getNextCelda(fila, columnaActual);
            celda.setCellValue(lote.getNumeroLote());

            celda = getNextCelda(fila, columnaActual);
            Ingreso ingreso = lote.getIngresosLote().get(i);
            try {
                celda.setCellValue(df.parse(df.format(ingreso.getFechaIngreso())));
            } catch (ParseException ex) {
            } catch (NullPointerException ex) {
                celda.setCellValue("");
            }

            celda.setCellStyle(estiloContenidoTablaFecha);

            celda = getNextCelda(fila, columnaActual);
            celda.setCellValue(ingreso.getNumeroFactura());

            celda = getNextCelda(fila, columnaActual);
            celda.setCellValue(ingreso.getCantidadIngreso());

            celda = getNextCelda(fila, columnaActual);
            celda.setCellValue(String.valueOf(lote.getSuministroLote().getUnidadSuministro().getNombreUnidad()));

            celda = getNextCelda(fila, columnaActual);
            try {
                celda.setCellValue(df.parse(df.format(lote.getVencimientoLote())));
            } catch (ParseException ex) {
            } catch (NullPointerException ex) {
                celda.setCellValue("");
            }
            celda.setCellStyle(estiloContenidoTablaFecha);

            celda = getNextCelda(fila, columnaActual);
            celda.setCellValue(ingreso.getObservacionesIngreso());

            i++;
        } while (i < lote.getIngresosLote().size());
    }

    private void AgregarRegistroSalida(Lote lote, Sheet hoja, int[] numFila) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy");
        Cell celda = null;
        Row fila = null;
        int[] columnaActual;
        int i = 0;
        // argg
        do {
            columnaActual = new int[]{0};
            fila = hoja.createRow(numFila[0]++);

            celda = getNextCelda(fila, columnaActual);
            celda.setCellValue(lote.getNumeroLote());

            celda = getNextCelda(fila, columnaActual);
            Salida salida = lote.getSalidasLote().get(i);
            try {
                celda.setCellValue(df.parse(df.format(salida.getFechaSalida())));
            } catch (ParseException ex) {
            } catch (NullPointerException ex) {
                celda.setCellValue("");
            }
            celda.setCellStyle(estiloContenidoTablaFecha);

            celda = getNextCelda(fila, columnaActual);
            celda.setCellValue(salida.getCantidadSalida());

            celda = getNextCelda(fila, columnaActual);
            celda.setCellValue(String.valueOf(lote.getSuministroLote().getUnidadSuministro().getNombreUnidad()));

            celda = getNextCelda(fila, columnaActual);
            try {
                celda.setCellValue(df.parse(df.format(lote.getVencimientoLote())));
            } catch (ParseException ex) {
            } catch (NullPointerException ex) {
                celda.setCellValue("");
            }
            celda.setCellStyle(estiloContenidoTablaFecha);

            celda = getNextCelda(fila, columnaActual);
            celda.setCellValue(salida.getObservacionesSalida());

            i++;
        } while (i < lote.getSalidasLote().size());
    }

    private void AgregarRegistroMovimiento(Lote lote, Sheet hoja, int[] numFila) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy");
        Cell celda = null;
        Row fila = null;
        int[] columnaActual;
        // argg

        columnaActual = new int[]{0};
        fila = hoja.createRow(numFila[0]++);

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(lote.getNumeroLote());

        celda = getNextCelda(fila, columnaActual);
        try {
            celda.setCellValue(df.parse(df.format(lote.getVencimientoLote())));
        } catch (ParseException ex) {
        } catch (NullPointerException ex) {
            celda.setCellValue("");
        }
        celda.setCellStyle(estiloContenidoTablaFecha);

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(lote.getCantidadIngresosLote());

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(lote.getCantidadSalidasLote());

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(lote.getCantidadStock());

        celda = getNextCelda(fila, columnaActual);
        celda.setCellValue(String.valueOf(lote.getSuministroLote().getUnidadSuministro().getNombreUnidad()));

    }

    private Cell getNextCelda(Row fila, int[] columnaActual) {
        Cell celda = fila.createCell(columnaActual[0]++);
        celda.setCellStyle(estiloContenidoTabla);
        return celda;
    }

    public void ExportarLibroExcelInfo(Suministro suministro, String tituloArchivo) {
        CrearHojaLibroExcelIngresos(suministro);
        CrearHojaLibroExcelSalidas(suministro);
        CrearHojaLibroExcelMovimientos(suministro);
        ExportarLibroExcel(tituloArchivo);
    }

    public void ExportarLibroExcelListado(List<Suministro> suministros, String tituloArchivo, TipoLibro tipo) {
        CrearHojaLibroExcel(suministros, tipo);
        ExportarLibroExcel(tituloArchivo);
    }
}
