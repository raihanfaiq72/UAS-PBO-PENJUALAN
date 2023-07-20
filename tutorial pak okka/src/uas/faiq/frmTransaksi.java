/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package uas.faiq;

/**
 *
 * @author FAIQ RAIHAN
 */
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.awt.print.PrinterException;
import java.sql.*;
import java.text.MessageFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class frmTransaksi extends javax.swing.JFrame {
    Connection Con;
    ResultSet RsBrg;
    ResultSet RsKons;
    Statement stm;
    double total=0;
    String tanggal;
    Boolean edit=false;
    DefaultTableModel tableModel = new DefaultTableModel(
        new Object [][] {},
        new String [] {
        "Kd Barang", "Nama Barang","Harga Barang","Jumlah","Total"
    });
		//Var Pencarian Kode Barang
    String idBrg;
    String namaBrg;
    String hargaBrg;
    /**
     * Creates new form frmTransaksi
     */
    //kosongi table penjualan
    private void aktif(boolean x)
{
    txtNoJual.setEnabled(x);
    txtNoJual.setEditable(false);
    
    txtNama.setEnabled(x);
    txtNama.setEditable(false);
    
    txtNm_Brg.setEnabled(x);
    txtNm_Brg.setEditable(false);
    
    txtHarga.setEnabled(x);
    txtHarga.setEditable(false);
    
    txtJml.setEnabled(x);
    txtTot.setEnabled(x);
    txtTot.setEditable(false);
    
    txtTotal.setEnabled(x);
    txtTotal.setEditable(false);
    
    txtBayar.setEnabled(x);
    
    txtKembali.setEnabled(x);
    txtKembali.setEditable(false);
    
    txtTot.setEnabled(x);
    txtTotal.setEnabled(x);
    txtId.setEnabled(x);
    
    cmbKd_Kons.setEnabled(x);
    cmbKd_Brg.setEnabled(x);
    txtTgl.setEnabled(x);
    txtJml.setEditable(x);
}

//method set tombol on/off
private void setTombol(boolean t)
{
    cmdTambah.setEnabled(t);
    cmdSimpan.setEnabled(!t);
    cmdBatal.setEnabled(!t);
    cmdKeluar.setEnabled(t);
    cmdHapusItem.setEnabled(!t);
    btnPilih.setEnabled(!t);
    
}
    private void kosong_table() {
        DefaultTableModel model = (DefaultTableModel) tblBrg.getModel();
        model.setRowCount(0); // Menghapus semua baris dalam tabel
    }
//    private void tblBrgMouseClicked(java.awt.event.MouseEvent evt) {                                    
//    int selectedRow = tblBrg.getSelectedRow();
//
//    // Check if a valid row is selected
//    if (selectedRow >= 0) {
//        String idBrg = tblBrg.getValueAt(selectedRow, 0).toString();
//        String namaBrg = tblBrg.getValueAt(selectedRow, 1).toString();
//        String hargaBrg = tblBrg.getValueAt(selectedRow, 3).toString();
//
//        fAB.idBrg = idBrg;
//        fAB.namaBrg = namaBrg;
//        fAB.hargaBrg = hargaBrg;
//        fAB.itemTerpilih();
//        this.dispose();
//    }
//}

    public frmTransaksi() {
      initComponents();
      open_db();
      inisialisasi_tabel();
      aktif(false);
      setTombol(true);
      txtTgl.setEditor(new JSpinner.DateEditor(txtTgl,"yyyy/MM/dd"));         
  }
    private void open_db()
    {
        try{
            KoneksiMysql kon = new KoneksiMysql("localhost","root","","pbo");
            Con = kon.getConnection();
        }
        catch (Exception e) {
            System.out.println("Error : "+e);
        }
    }
    
//    private void baca_data()
//    {
//        open_db();
//        try{
//            stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            RsBrg = stm.executeQuery("select * from barang order by kd_brg");
//            
//            ResultSetMetaData meta = RsBrg.getMetaData();
//            int col = meta.getColumnCount();
//            int baris = 0;
//            while(RsBrg.next()) {
//                baris = RsBrg.getRow();
//            }
//
//            dataTable = new Object[baris][col];
//            int x = 0;
//            RsBrg.beforeFirst();
//            while(RsBrg.next()) {
//                dataTable[x][0] = RsBrg.getString("kd_brg");
//                dataTable[x][1] = RsBrg.getString("nm_brg");
//                dataTable[x][2] = RsBrg.getString("satuan");
//                dataTable[x][3] = RsBrg.getDouble("harga");
//                dataTable[x][4] = RsBrg.getDouble("harga_beli");
//                dataTable[x][5] = RsBrg.getInt("stok");
//                dataTable[x][6] = RsBrg.getInt("stok_min");
//                x++;
//            }
//            tblBrg.setModel(new DefaultTableModel(dataTable,header));
//        }
//        catch(SQLException e)
//        {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
    
    
    //method hitung penjualan
private void hitung_jual()
{
    double xtot,xhrg;
    int xjml;

    xhrg=Double.parseDouble(txtHarga.getText());
    xjml=Integer.parseInt(txtJml.getText());
    xtot=xhrg*xjml;
    String xtotal=Double.toString(xtot);
    txtTot.setText(xtotal);
    total=total+xtot;
    //txtTotal.setText(Double.toString(total));
}

//methohd baca data konsumen
private void baca_konsumen()
{
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

    try {
        stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        RsKons = stm.executeQuery("SELECT kd_kons FROM konsumen");

        while (RsKons.next()) {
            String kodeKonsumen = RsKons.getString("kd_kons");
            model.addElement(kodeKonsumen);
        }

				RsKons.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    cmbKd_Kons.setModel(model);
}

private void baca_barang() {
DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

try {
    stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    RsBrg = stm.executeQuery("SELECT kd_brg FROM barang");

    while (RsBrg.next()) {
        String kodeBarang = RsBrg.getString("kd_brg");
        model.addElement(kodeBarang);
    }

    RsBrg.close();
} catch (SQLException e) {
    e.printStackTrace();
}

cmbKd_Brg.setModel(model);
}

//method baca barang setelah combo barang di klik
private void detail_barang(String xkode){            
    try {
        stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        RsBrg = stm.executeQuery("select * from barang where kd_brg = '" +xkode+ "'");

        if (RsBrg.next()) {
            String namaBrg = RsBrg.getString("nm_brg");
            int hargaBrg = RsBrg.getInt("harga");

            txtNm_Brg.setText(namaBrg);
            txtHarga.setText(Integer.toString(hargaBrg));
        } else {
            txtNm_Brg.setText("");
            txtHarga.setText("");
        }

        RsBrg.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e);
    }
}

//method baca konsumen setelah combo konsumen di klik
private void detail_konsumen(String xkode)
{
    try {
        stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        RsKons = stm.executeQuery("select * from konsumen where kd_kons = '" +xkode+ "'");

        if (RsKons.next()) {
            String namaKons = RsKons.getString("nm_kons");
            txtNama.setText(namaKons);
        } else {
            txtNama.setText("");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e);
    }
}

//method set model tabel
    public void inisialisasi_tabel() {
        tblBrg.setModel(tableModel);
    }

//method pengkosongan isian
private void kosong()
{
    txtNoJual.setText("");
    txtNama.setText("");
    txtHarga.setText("");
    //txtTotal.setText("");
}

//method kosongkan detail jual
private void kosong_detail()
{
    txtNm_Brg.setText("");
    txtHarga.setText("");
    txtJml.setText("");
    txtTot.setText("");
}

//method buat nomor jual otomatis
private void nomor_jual()
{
    try{
        stm=Con.createStatement();
        ResultSet rs=stm.executeQuery("select no_jual from jual");
        int brs=0;

        while(rs.next())
        {
            brs=rs.getRow();
        }
        if(brs==0)
            txtNoJual.setText("1");
        else
        {int nom=brs+1;
            txtNoJual.setText(Integer.toString(nom));
        }
        rs.close();
    }
    catch(SQLException e)
    {
        System.out.println("Error : "+e);
    }
}

//method simpan detail jual di tabel (temporary)
private void simpan_ditabel()
{
    try{
        String tKode=cmbKd_Brg.getSelectedItem().toString();
        String tNama=txtNm_Brg.getText();
        double hrg=Double.parseDouble(txtHarga.getText());
        int jml=Integer.parseInt(txtJml.getText());
        double tot=Double.parseDouble(txtTot.getText());
        tableModel.addRow(new Object[]{tKode,tNama,hrg,jml,tot});
        inisialisasi_tabel();
    }
    catch(Exception e)
    {
        System.out.println("Error : "+e);
    }
}

//method simpan transaksi penjualan pada table di MySql
private void simpan_transaksi() {
    try {
        String xnojual = txtNoJual.getText();
        //format_tanggal();
        String xkode = cmbKd_Kons.getSelectedItem().toString();

        // Menghitung pajak
        double pajak = 0.1; // 10% pajak
        String sql = "SELECT * FROM barang";
        ResultSet rsBrg = stm.executeQuery(sql);
        while (rsBrg.next()) {
            double harga = rsBrg.getDouble("harga");
            pajak += harga * 0.1; // Menambahkan 10% harga barang ke pajak
        }

        String msql = "INSERT INTO jual VALUES ('" + xnojual + "','" + xkode + "','" + tanggal + "')";
        stm.executeUpdate(msql);
        for (int i = 0; i < tblBrg.getRowCount(); i++) {
            String xkd = (String) tblBrg.getValueAt(i, 0);
            double xhrg = (Double) tblBrg.getValueAt(i, 2);
            int xjml = (Integer) tblBrg.getValueAt(i, 3);
            String zsql = "INSERT INTO djual VALUES ('" + xnojual + "','" + xkd + "'," + xhrg + "," + xjml + ")";
            stm.executeUpdate(zsql);
        }

        JOptionPane.showMessageDialog(null, "Data penjualan berhasil disimpan.");
    } catch (SQLException e) {
        System.out.println("Error : " + e);
    }
    }

//method membuat format tanggal sesuai dengan MySQL
private void format_tanggal()
{
    String DATE_FORMAT = "yyyy-MM-dd";
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
    Calendar c1 = Calendar.getInstance();
    int year=c1.get(Calendar.YEAR);
    int month=c1.get(Calendar.MONTH)+1;
    int day=c1.get(Calendar.DAY_OF_MONTH);
    tanggal=Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
}

//cetak gaes
private class PrintingTask extends SwingWorker<Object, Object> {
  private final MessageFormat headerFormat;
  private final MessageFormat footerFormat;
  private final boolean interactive;
  private volatile boolean complete = false;
  private volatile String message;

  public PrintingTask(MessageFormat header, MessageFormat footer, boolean interactive) {
      this.headerFormat = header;
      this.footerFormat = footer;
      this.interactive = interactive;
  }

  @Override
  protected Object doInBackground() {
      try {
          complete = text.print(headerFormat, footerFormat,
          true, null, null, interactive);
          message = "Printing " + (complete ? "complete" : "canceled");
      } catch (PrinterException ex) {
          message = "Sorry, a printer error occurred";
      } catch (SecurityException ex) {
      message = "Sorry, cannot access the printer due to security reasons";
      }
      return null;
  }
  
  @Override
  protected void done() {
      showMessage(!complete, message);
  }
}

private void showMessage(boolean isError, String message) {
  if (isError) {
      JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  } else {
      JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
  }
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNoJual = new javax.swing.JTextField();
        txtTgl = new javax.swing.JSpinner();
        cmbKd_Kons = new javax.swing.JComboBox<>();
        txtNama = new javax.swing.JTextField();
        cmbKd_Brg = new javax.swing.JComboBox<>();
        txtNm_Brg = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        txtJml = new javax.swing.JTextField();
        txtTot = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBrg = new javax.swing.JTable();
        cmdHapusItem = new javax.swing.JButton();
        cmdTambah = new javax.swing.JButton();
        cmdSimpan = new javax.swing.JButton();
        cmdBatal = new javax.swing.JButton();
        cmdCetak = new javax.swing.JButton();
        cmdKeluar = new javax.swing.JButton();
        btnPilih = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtBayar = new javax.swing.JTextField();
        txtKembali = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("No Jual");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Tanggal Jual");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Kode Konsumen");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Nama Konsumen");

        txtTgl.setModel(new javax.swing.SpinnerDateModel());

        cmbKd_Kons.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbKd_Kons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKd_KonsActionPerformed(evt);
            }
        });

        cmbKd_Brg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblBrg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Harga", "Jumlah"
            }
        ));
        jScrollPane1.setViewportView(tblBrg);

        cmdHapusItem.setText("Hapus Item");
        cmdHapusItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdHapusItemActionPerformed(evt);
            }
        });

        cmdTambah.setText("Tambah");
        cmdTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTambahActionPerformed(evt);
            }
        });

        cmdSimpan.setText("Simpan");
        cmdSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSimpanActionPerformed(evt);
            }
        });

        cmdBatal.setText("Batal");
        cmdBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBatalActionPerformed(evt);
            }
        });

        cmdCetak.setText("Cetak");
        cmdCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCetakActionPerformed(evt);
            }
        });

        cmdKeluar.setText("Keluar");
        cmdKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeluarActionPerformed(evt);
            }
        });

        btnPilih.setText("Pilih Barang");
        btnPilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihActionPerformed(evt);
            }
        });

        text.setColumns(20);
        text.setRows(5);
        jScrollPane2.setViewportView(text);

        jLabel5.setText("Total Bayar");

        jLabel6.setText("Bayar");

        jLabel7.setText("Kembali");

        txtBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBayarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbKd_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(txtNm_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(txtHarga))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNoJual)
                                    .addComponent(txtTgl, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                                .addGap(91, 91, 91)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbKd_Kons, 0, 153, Short.MAX_VALUE)
                                    .addComponent(txtNama)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtJml, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtTot, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmdHapusItem)
                        .addGap(61, 61, 61)
                        .addComponent(btnPilih)
                        .addGap(18, 18, 18)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmdTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdBatal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdCetak)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdKeluar)
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotal)
                            .addComponent(txtBayar)
                            .addComponent(txtKembali))))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(txtNoJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbKd_Kons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbKd_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNm_Brg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtJml, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdHapusItem)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPilih)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmdTambah)
                                .addComponent(cmdSimpan)
                                .addComponent(cmdBatal)
                                .addComponent(cmdCetak)
                                .addComponent(cmdKeluar))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 80, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbKd_KonsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKd_KonsActionPerformed
        // TODO add your handling code here:
         String kdBrg = cmbKd_Brg.getSelectedItem().toString();
        detail_barang(kdBrg);
    }//GEN-LAST:event_cmbKd_KonsActionPerformed

    private void cmdHapusItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdHapusItemActionPerformed
        // TODO add your handling code here:
            try {
        int row = tblBrg.getSelectedRow(); // Mendapatkan baris yang dipilih

        if (row != -1) { // Memastikan ada baris yang dipilih
            tableModel.removeRow(row); // Menghapus baris dari tableModel
            inisialisasi_tabel(); // Memperbarui tampilan tabel
        } else {
            JOptionPane.showMessageDialog(null, "Pilih baris yang ingin dihapus");
        }
    } catch (Exception e) {
        System.out.println("Error: " + e);
    }
    }//GEN-LAST:event_cmdHapusItemActionPerformed

    private void cmdTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTambahActionPerformed
        // TODO add your handling code here:
    aktif(true);
    setTombol(false);
    baca_barang();
    baca_konsumen();
    kosong();
    kosong_detail();
    kosong_table();
    nomor_jual();       
    }//GEN-LAST:event_cmdTambahActionPerformed

    private void cmdSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSimpanActionPerformed
        // TODO add your handling code here:                                        
        simpan_transaksi();
        inisialisasi_tabel();
    }//GEN-LAST:event_cmdSimpanActionPerformed

    private void cmdBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBatalActionPerformed
        // TODO add your handling code here:                                        
        aktif(false);
    setTombol(true);
    kosong();
    kosong_detail();
    kosong_table();
    text.setText("");
    }//GEN-LAST:event_cmdBatalActionPerformed

    private void cmdKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKeluarActionPerformed
        // TODO add your handling code here:
       // Dispose();
    }//GEN-LAST:event_cmdKeluarActionPerformed

    private void cmdCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCetakActionPerformed
        // TODO add your handling code here:
        format_tanggal();
    String ctk="Nota Penjualan\nNo:"+txtNoJual.getText()+"\nTanggal : "+tanggal;
    ctk=ctk+"\n"+"--------------------------------------------------------------------------------------------------------------------------------";
    ctk=ctk+"\n"+"Kode\tNama Barang\t\tHarga\tJml\tTotal";
    ctk=ctk+"\n"+"--------------------------------------------------------------------------------------------------------------------------------";

    for(int i=0;i<tblBrg.getRowCount();i++)
    {
        String xkd=(String)tblBrg.getValueAt(i,0);
        String xnama=(String)tblBrg.getValueAt(i,1);
        double xhrg=(Double)tblBrg.getValueAt(i,2);
        int xjml=(Integer)tblBrg.getValueAt(i,3);
        double xtot=(Double)tblBrg.getValueAt(i,4);
        ctk=ctk+"\n"+xkd+"\t"+xnama+"\t\t"+xhrg+"\t"+xjml+"\t"+xtot;
    }

    ctk=ctk+"\n"+"--------------------------------------------------------------------------------------------------------------------------------";
    ctk=ctk+"\n\t\t\t\t\t"+txtTotal.getText();
    text.setText(ctk);

    String headerField="";
    String footerField="";
    MessageFormat header = new MessageFormat(headerField);
    MessageFormat footer = new MessageFormat(footerField);
    boolean interactive = true;//interactiveCheck.isSelected();
    boolean background = true;//backgroundCheck.isSelected();
    PrintingTask task = new PrintingTask(header, footer, interactive);

    if (background) {
        task.execute();
    } else {
        task.run();
    }
    }//GEN-LAST:event_cmdCetakActionPerformed

    private void btnPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihActionPerformed
        // TODO add your handling code here:
            frmSelectBarang fDB = new frmSelectBarang();
            fDB.fAB = this;
            fDB.setVisible(true);
            fDB.setResizable(false);
    }//GEN-LAST:event_btnPilihActionPerformed

    private void txtBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBayarActionPerformed
        // TODO add your handling code here:
        hitung_bayar();
    }//GEN-LAST:event_txtBayarActionPerformed

    public void itemTerpilih() {
        frmSelectBarang fDB = new frmSelectBarang();
        fDB.fAB = this;
        txtId.setText(idBrg);
        cmbKd_Brg.setSelectedItem(idBrg);
        txtNm_Brg.setText(namaBrg);
        txtHarga.setText(hargaBrg);
        // txtTot.setText(txtTotal);
    }

//Menghitung Kembalian
private void hitung_bayar(){
    double xtotal,xbayar, xkembali;

    xtotal=Double.parseDouble(txtTotal.getText());
    xbayar=Double.parseDouble(txtBayar.getText());        
    xkembali=xbayar-xtotal;
    String xkembalixx=Double.toString(xkembali);
    txtKembali.setText(xkembalixx);
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPilih;
    private javax.swing.JComboBox<String> cmbKd_Brg;
    private javax.swing.JComboBox<String> cmbKd_Kons;
    private javax.swing.JButton cmdBatal;
    private javax.swing.JButton cmdCetak;
    private javax.swing.JButton cmdHapusItem;
    private javax.swing.JButton cmdKeluar;
    private javax.swing.JButton cmdSimpan;
    private javax.swing.JButton cmdTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblBrg;
    private javax.swing.JTextArea text;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtJml;
    private javax.swing.JTextField txtKembali;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNm_Brg;
    private javax.swing.JTextField txtNoJual;
    private javax.swing.JSpinner txtTgl;
    private javax.swing.JTextField txtTot;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
