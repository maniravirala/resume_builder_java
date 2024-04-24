/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.cv;

/**
 *
 * @author ravir
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ResumeBuilder extends JFrame {

    private JTextField nameField, addressField, mobileField, githubField, linkedinField;
    private JTextArea summaryArea;
    private List<JPanel> educationPanels, summerTrainingPanels, internshipPanels, projectPanels, technicalSkillPanels;

    public ResumeBuilder() {
        setTitle("Resume Builder");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main content panel with vertical scrolling
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Personal Details Panel
        JPanel personalDetailsPanel = new JPanel(new GridLayout(5, 2));
        personalDetailsPanel.setBorder(BorderFactory.createTitledBorder("Personal Details"));
        personalDetailsPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        personalDetailsPanel.add(nameField);
        personalDetailsPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        personalDetailsPanel.add(addressField);
        personalDetailsPanel.add(new JLabel("Mobile No:"));
        mobileField = new JTextField();
        personalDetailsPanel.add(mobileField);
        personalDetailsPanel.add(new JLabel("GitHub Link:"));
        githubField = new JTextField();
        personalDetailsPanel.add(githubField);
        personalDetailsPanel.add(new JLabel("LinkedIn Link:"));
        linkedinField = new JTextField();
        personalDetailsPanel.add(linkedinField);
        contentPanel.add(personalDetailsPanel, gbc);

        // Summary Panel
        gbc.gridy++;
        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        summaryArea = new JTextArea(5, 20);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);
        JScrollPane summaryScrollPane = new JScrollPane(summaryArea);
        summaryPanel.add(summaryScrollPane, BorderLayout.CENTER);
        contentPanel.add(summaryPanel, gbc);

        // Initialize section panels
        educationPanels = new ArrayList<>();
        summerTrainingPanels = new ArrayList<>();
        internshipPanels = new ArrayList<>();
        projectPanels = new ArrayList<>();
        technicalSkillPanels = new ArrayList<>();

        gbc.gridy++;
        contentPanel.add(createSectionPanel("Education", educationPanels, "Add Education", "Remove Education"), gbc);

        gbc.gridy++;
        contentPanel.add(createSectionPanel("Summer Training", summerTrainingPanels, "Add Summer Training",
                "Remove Summer Training"), gbc);

        gbc.gridy++;
        contentPanel.add(createSectionPanel("Internships", internshipPanels, "Add Internship", "Remove Internship"),
                gbc);

        gbc.gridy++;
        contentPanel.add(createSectionPanel("Projects", projectPanels, "Add Project", "Remove Project"), gbc);

        gbc.gridy++;
        contentPanel.add(createSectionPanel("Technical Skills", technicalSkillPanels, "Add Technical Skill",
                "Remove Technical Skill"), gbc);

        // Generate Pdf Button
        gbc.gridy++;
        JButton generatePDFButton = new JButton("Generate PDF");
        generatePDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePDF();
            }
        });
        contentPanel.add(generatePDFButton, gbc);
        setVisible(true);
    }

    private JPanel createSectionPanel(String title, List<JPanel> panelsList, String addButtonLabel,
            String removeButtonLabel) {
        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setBorder(BorderFactory.createTitledBorder(title));

        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));
        panelsList.add(createEntryPanel(title));
        entryPanel.add(panelsList.get(0));
        sectionPanel.add(entryPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton(addButtonLabel);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsList.add(createEntryPanel(title));
                entryPanel.add(panelsList.get(panelsList.size() - 1));
                entryPanel.revalidate();
                entryPanel.repaint();
            }
        });
        buttonPanel.add(addButton);

        JButton removeButton = new JButton(removeButtonLabel);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelsList.size() > 1) {
                    entryPanel.remove(panelsList.get(panelsList.size() - 1));
                    panelsList.remove(panelsList.size() - 1);
                    entryPanel.revalidate();
                    entryPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "At least one " + title.toLowerCase() + " entry is required");
                }
            }
        });
        buttonPanel.add(removeButton);

        sectionPanel.add(buttonPanel, BorderLayout.SOUTH);

        return sectionPanel;
    }

    private JPanel createEntryPanel(String section) {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel(section + " Title:"));
        JTextField titleField = new JTextField();
        panel.add(titleField);

        switch (section) {
            case "Education":
                panel.add(new JLabel("School:"));
                JTextField schoolField = new JTextField();
                panel.add(schoolField);
                panel.add(new JLabel("Degree:"));
                JTextField degreeField = new JTextField();
                panel.add(degreeField);
                panel.add(new JLabel("Start Date:"));
                JTextField startDateFieldEdu = new JTextField();
                panel.add(startDateFieldEdu);
                panel.add(new JLabel("End Date:"));
                JTextField endDateFieldEdu = new JTextField();
                panel.add(endDateFieldEdu);
                panel.add(new JLabel("CGPA:"));
                JTextField cgpaField = new JTextField();
                panel.add(cgpaField);
                panel.add(new JLabel("Location:"));
                JTextField locationFieldEdu = new JTextField();
                panel.add(locationFieldEdu);
                break;

            case "Summer Training":
            case "Internships":
                panel.add(new JLabel("Organization:"));
                JTextField orgField = new JTextField();
                panel.add(orgField);
                panel.add(new JLabel("Location:"));
                JTextField locField = new JTextField();
                panel.add(locField);
                panel.add(new JLabel("Date:"));
                JTextField dateField = new JTextField();
                panel.add(dateField);
                panel.add(new JLabel("Description:"));
                JTextArea descArea = new JTextArea(3, 20);
                descArea.setLineWrap(true);
                descArea.setWrapStyleWord(true);
                panel.add(new JScrollPane(descArea));
                break;

            case "Projects":
                panel.add(new JLabel("Domain:"));
                JTextField domainField = new JTextField();
                panel.add(domainField);
                panel.add(new JLabel("Technologies:"));
                JTextField techField = new JTextField();
                panel.add(techField);
                panel.add(new JLabel("Date:"));
                JTextField dateFieldProj = new JTextField();
                panel.add(dateFieldProj);
                panel.add(new JLabel("Description:"));
                JTextArea descAreaProj = new JTextArea(3, 20);
                descAreaProj.setLineWrap(true);
                descAreaProj.setWrapStyleWord(true);
                panel.add(new JScrollPane(descAreaProj));
                break;

            case "Technical Skills":
                panel.add(new JLabel("Domain:"));
                JTextField domainFieldTS = new JTextField();
                panel.add(domainFieldTS);
                panel.add(new JLabel("Skills:"));
                JTextArea skillsArea = new JTextArea(3, 20);
                skillsArea.setLineWrap(true);
                skillsArea.setWrapStyleWord(true);
                panel.add(new JScrollPane(skillsArea));
                break;
        }

        return panel;
    }

    private void generatePDF() {
        Document document = new Document();
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save PDF");
            fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave.getAbsolutePath() + ".pdf"));
                document.open();

                // Add content to PDF
                document.add(new Paragraph("Resume"));

                // Personal Details
                document.add(new Paragraph("Personal Details"));
                document.add(new Paragraph("Name: " + nameField.getText()));
                document.add(new Paragraph("Address: " + addressField.getText()));
                document.add(new Paragraph("Mobile No: " + mobileField.getText()));
                document.add(new Paragraph("GitHub Link: " + githubField.getText()));
                document.add(new Paragraph("LinkedIn Link: " + linkedinField.getText()));
                addSectionLine(document);

                // Summary
                document.add(new Paragraph("Summary"));
                document.add(new Paragraph(summaryArea.getText()));
                addSectionLine(document);

                // Education
                document.add(new Paragraph("Education"));
                for (JPanel panel : educationPanels) {
                    JTextField titleField = (JTextField) panel.getComponent(1);
                    JTextField schoolField = (JTextField) panel.getComponent(3);
                    JTextField degreeField = (JTextField) panel.getComponent(5);
                    JTextField startDateFieldEdu = (JTextField) panel.getComponent(7);
                    JTextField endDateFieldEdu = (JTextField) panel.getComponent(9);
                    JTextField cgpaField = (JTextField) panel.getComponent(11);
                    JTextField locationFieldEdu = (JTextField) panel.getComponent(13);
                    document.add(new Paragraph("Title: " + titleField.getText()));
                    document.add(new Paragraph("School: " + schoolField.getText()));
                    document.add(new Paragraph("Degree: " + degreeField.getText()));
                    document.add(new Paragraph("Start Date: " + startDateFieldEdu.getText()));
                    document.add(new Paragraph("End Date: " + endDateFieldEdu.getText()));
                    document.add(new Paragraph("CGPA: " + cgpaField.getText()));
                    document.add(new Paragraph("Location: " + locationFieldEdu.getText()));
                    addSectionLine(document);

                }

                // Summer Training
                document.add(new Paragraph("Summer Training"));
                for (JPanel panel : summerTrainingPanels) {
                    JTextField titleField = (JTextField) panel.getComponent(1);
                    JTextField orgField = (JTextField) panel.getComponent(3);
                    JTextField locField = (JTextField) panel.getComponent(5);
                    JTextField dateField = (JTextField) panel.getComponent(7);
                    JTextArea descArea = (JTextArea) ((JScrollPane) panel.getComponent(9)).getViewport().getView();
                    document.add(new Paragraph("Title: " + titleField.getText()));
                    document.add(new Paragraph("Organization: " + orgField.getText()));
                    document.add(new Paragraph("Location: " + locField.getText()));
                    document.add(new Paragraph("Date: " + dateField.getText()));
                    document.add(new Paragraph("Description: " + descArea.getText()));
                    addSectionLine(document);                    
                }

                // Internships
                document.add(new Paragraph("Internships"));
                for (JPanel panel : internshipPanels) {
                    JTextField titleField = (JTextField) panel.getComponent(1);
                    JTextField orgField = (JTextField) panel.getComponent(3);
                    JTextField locField = (JTextField) panel.getComponent(5);
                    JTextField dateField = (JTextField) panel.getComponent(7);
                    JTextArea descArea = (JTextArea) ((JScrollPane) panel.getComponent(9)).getViewport().getView();

                    document.add(new Paragraph("Title: " + titleField.getText()));
                    document.add(new Paragraph("Organization: " + orgField.getText()));
                    document.add(new Paragraph("Location: " + locField.getText()));
                    document.add(new Paragraph("Date: " + dateField.getText()));
                    document.add(new Paragraph("Description: " + descArea.getText()));
                    addSectionLine(document);
                }

                // Projects
                document.add(new Paragraph("Projects"));
                for (JPanel panel : projectPanels) {
                    JTextField titleField = (JTextField) panel.getComponent(1);
                    JTextField domainField = (JTextField) panel.getComponent(3);
                    JTextField techField = (JTextField) panel.getComponent(5);
                    JTextField dateFieldProj = (JTextField) panel.getComponent(7);
                    JTextArea descAreaProj = (JTextArea) ((JScrollPane) panel.getComponent(9)).getViewport().getView();

                    document.add(new Paragraph("Title: " + titleField.getText()));
                    document.add(new Paragraph("Domain: " + domainField.getText()));
                    document.add(new Paragraph("Technologies: " + techField.getText()));
                    document.add(new Paragraph("Date: " + dateFieldProj.getText()));
                    document.add(new Paragraph("Description: " + descAreaProj.getText()));
                    addSectionLine(document);
                }

                // Technical Skills
                document.add(new Paragraph("Technical Skills"));
                for (JPanel panel : technicalSkillPanels) {
                    JTextField titleField = (JTextField) panel.getComponent(1);
                    JTextField domainFieldTS = (JTextField) panel.getComponent(3);
                    JTextArea skillsArea = (JTextArea) ((JScrollPane) panel.getComponent(5)).getViewport().getView();

                    document.add(new Paragraph("Title: " + titleField.getText()));
                    document.add(new Paragraph("Domain: " + domainFieldTS.getText()));
                    document.add(new Paragraph("Skills: " + skillsArea.getText()));
                    addSectionLine(document);
                }

                document.close();
                JOptionPane.showMessageDialog(null, "PDF saved successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving PDF.");
        }
    }

    private void addSectionLine(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell(new Phrase(" "));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        cell.setMinimumHeight(10);
        table.addCell(cell);
        document.add(table);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ResumeBuilder());
    }
}
