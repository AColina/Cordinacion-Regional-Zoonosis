/*
 * Copyright 2016 Gustavo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ve.zoonosis.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *
 * @author Gustavo
 */
public class PDFCreator {

    private PDDocument document = new PDDocument();
    private float margin = 50;
    private int linePos = 1;
    PDPage pageActual = null;
    PDPageContentStream contentStream = null;

    public void addPage() throws IOException {
        pageActual = new PDPage();
        document.addPage(pageActual);
        contentStream = new PDPageContentStream(document, pageActual);
        linePos = 1;
    }

    public void clearPage() throws IOException {
        if (pageActual != null) {
            document.removePage(pageActual);
            pageActual = new PDPage();
            document.addPage(pageActual);
            contentStream = new PDPageContentStream(document, pageActual);
            linePos = 1;
        }
    }

    public void addLeftText(String text) throws IOException {
        addLeftText(PDType1Font.HELVETICA, 12, text);
    }

    public void addLeftText(PDFont font, String text) throws IOException {
        addLeftText(font, 12, text);
    }

    public void addLeftText(int fontSize, String text) throws IOException {
        addLeftText(PDType1Font.HELVETICA, fontSize, text);
    }

    public void addLeftText(PDFont font, int fontSize, String text) throws IOException {
        if (contentStream == null) {
            addPage();
        }
        contentStream.setFont(font, fontSize);
        List<String> lines = new ArrayList<>();
        int lastSpace = -1;
        float width = pageActual.getMediaBox().getWidth() - (2 * margin);
        while (text.length() > 0) {
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0) {
                spaceIndex = text.length();
            }
            String subString = text.substring(0, spaceIndex);
            float size = 12 * font.getStringWidth(subString) / 1000;
            if (size > width) {
                if (lastSpace < 0) {
                    lastSpace = spaceIndex;
                }
                subString = text.substring(0, lastSpace);
                lines.add(subString);
                text = text.substring(lastSpace).trim();
                lastSpace = -1;
            } else if (spaceIndex == text.length()) {
                lines.add(text);
                text = "";
            } else {
                lastSpace = spaceIndex;
            }
        }

        for (String line : lines) {
            addLineText(line);
        }
    }

    public void addCenterText(String text) throws IOException {
        addCenterText(PDType1Font.HELVETICA_BOLD, 16, text);
    }

    public void addCenterText(PDFont font, String text) throws IOException {
        addCenterText(font, 16, text);
    }

    public void addCenterText(int fontSize, String text) throws IOException {
        addCenterText(PDType1Font.HELVETICA_BOLD, fontSize, text);
    }

    public void addCenterText(PDFont font, int fontSize, String text) throws IOException {
        if (contentStream == null) {
            addPage();
        }
        float titleWidth = font.getStringWidth(text) / 1000 * fontSize;
        float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
        contentStream.setFont(font, fontSize);
        contentStream.beginText();
        contentStream.moveTextPositionByAmount((pageActual.getMediaBox().getWidth() - titleWidth) / 2, 750 - (20 * linePos++));
        contentStream.drawString(text);
        contentStream.endText();
    }

    public void addNewLine() throws IOException {
        contentStream.beginText();
        contentStream.moveTextPositionByAmount(margin, 750 - (20 * linePos++));
        contentStream.drawString("");
        contentStream.endText();
    }

    private void addLineText(String text) throws IOException {
        contentStream.beginText();
        contentStream.moveTextPositionByAmount(margin, 750 - (20 * linePos++));
        contentStream.drawString(text);
        contentStream.endText();
    }

    public BufferedImage getImagePage(int index) throws IOException {
        contentStream.close();
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        return pdfRenderer.renderImage(index,1f,ImageType.RGB);
    }

    public void saveDocument(String path) throws IOException {
        contentStream.close();
        document.save(path);

    }

    public PDDocument getDocument() {
        return document;
    }

//    public static void main(String[] args) throws IOException {
//        PDFCreator pdfc = new PDFCreator();
//        pdfc.addPage();
//       
//        pdfc.saveDocument("C:\\Users\\Gustavo\\Desktop\\temp.pdf");
//    }
    public void drawTable(String[][] content) throws IOException {
        if (pageActual == null) {
            addPage();
        }
        float y = 750 - (20 * linePos++);
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 20f;
        final float tableWidth = pageActual.getMediaBox().getWidth() - margin - margin;
        final float tableHeight = rowHeight * rows;
        final float colWidth = tableWidth / (float) cols;
        final float cellMargin = 5f;

        //draw the rows
        float nexty = y;
        for (int i = 0; i <= rows; i++) {
            contentStream.drawLine(margin, nexty, margin + tableWidth, nexty);
            nexty -= rowHeight;
            linePos++;
        }

        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.drawLine(nextx, y, nextx, y - tableHeight);
            nextx += colWidth;
        }

        //now add the text        
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

        float textx = margin + cellMargin;
        float texty = y - 15;
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[i].length; j++) {
                String text = content[i][j];
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(textx, texty);
                contentStream.drawString(text);
                contentStream.endText();
                textx += colWidth;
            }
            texty -= rowHeight;
            textx = margin + cellMargin;
        }
    }
}
