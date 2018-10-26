package br.com.renaninfo.calccomp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.renaninfo.calccomp.adapter.ExpandListAdapter;
import br.com.renaninfo.calccomp.components.Child;
import br.com.renaninfo.calccomp.components.Group;

public class MainActivity extends AppCompatActivity {

    private static final String EMPTY = "";
    private static final Integer MAX_SIMULACOES = 3;
    private static final String LISTA_OPERADORES = "+-x÷=";

    private ExpandListAdapter expListAdapter;
    private ArrayList<Group> expListItems;
    private ExpandableListView expandListView;

    private TextView lblVisor;
    private TextView lblVisorPrincipal;

    private Button btnG1;
    private Button btnG2;
    private Button btnG3;
    private Button btnG4;

    private String esquerda;
    private String operador;
    private String direita;
    private String total;
    private Boolean isEsquerda;

    private Boolean jaFezCalculo = false;

    private Integer selectedGroup;
    private Integer qtyGroups = 0;
    private Boolean wasG1created = false;
    private Boolean wasG2created = false;
    private Boolean wasG3created = false;
    private Boolean wasG4created = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnG1 = findViewById(R.id.btnG1);
        btnG2 = findViewById(R.id.btnG2);
        btnG3 = findViewById(R.id.btnG3);
        btnG4 = findViewById(R.id.btnG4);

        expandListView = (ExpandableListView) findViewById(R.id.exp_list);
        expListItems = setStandardGroups();
        expListAdapter = new ExpandListAdapter(MainActivity.this, expListItems);
        expandListView.setAdapter(expListAdapter);

        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String group_name = expListItems.get(groupPosition).getName();
                ArrayList<Child> ch_list = expListItems.get(groupPosition).getItems();
                String child_name = ch_list.get(childPosition).getName();
                showToastMsg("G:" + group_name + "\nC:" + child_name);

                expListItems.get(groupPosition).setName(child_name);
                expListAdapter.notifyDataSetChanged();

                return false;
            }
        });

        expandListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                int childPosition = ExpandableListView.getPackedPositionChild(id);

                String group_name = expListItems.get(groupPosition).getName();
                ArrayList<Child> ch_list = expListItems.get(groupPosition).getItems();
                String child_name = ch_list.get(childPosition).getName();
                showToastMsg("Long Click on G:" + group_name + "\nC:" + child_name);

                lblVisor.setText(child_name);

                return false;
            }
        });

        expandListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                String group_name = expListItems.get(groupPosition).getName();
                showToastMsg("G:" + group_name + "\n Expanded");
            }
        });

        expandListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                String group_name = expListItems.get(groupPosition).getName();
                showToastMsg("G:" + group_name + "\n Collapsed");
            }
        });

        lblVisor = findViewById(R.id.lblVisor);
        lblVisorPrincipal = findViewById(R.id.lblVisorPrincipal);

        isEsquerda = true;
    }

    public ArrayList<Group> setStandardGroups() {
        ArrayList<Group> group_list = new ArrayList<Group>();
        return group_list;
    }

    public void showToastMsg(String Msg) {
        Toast.makeText(getApplicationContext(), Msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            esquerda = null;
            operador = null;
            direita = null;
            total = null;

            jaFezCalculo = false;
            isEsquerda = true;

            expListItems.clear();
            expListAdapter.notifyDataSetChanged();

            lblVisor.setText(EMPTY);
            lblVisorPrincipal.setText(EMPTY);

            selectedGroup = 0;
            wasG1created = false;
            wasG2created = false;
            wasG3created = false;
            wasG4created = false;

            expListItems = setStandardGroups();
            expListAdapter.notifyDataSetChanged();

            setActiveGroupButton();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btn0OnClick(View view) {
        processaNumero("0");
    }

    public void btn1OnClick(View view) {
        processaNumero("1");
    }

    public void btn2OnClick(View view) {
        processaNumero("2");
    }

    public void btn3OnClick(View view) {
        processaNumero("3");
    }

    public void btn4OnClick(View view) {
        processaNumero("4");
    }

    public void btn5OnClick(View view) {
        processaNumero("5");
    }

    public void btn6OnClick(View view) {
        processaNumero("6");
    }

    public void btn7OnClick(View view) {
        processaNumero("7");
    }

    public void btn8OnClick(View view) {
        processaNumero("8");
    }

    public void btn9OnClick(View view) {
        processaNumero("9");
    }

    public void btnVirgulaOnClick(View view) {
    }

    public void btnDelOnClick(View view) {
        if (!EMPTY.equals(lblVisor.getText().toString())) {
            lblVisor.setText(lblVisor.getText().toString().substring(0, lblVisor.getText().toString().length() - 1)); // 2

            if (EMPTY.equals(lblVisor.getText().toString())) {
                btnLimpaOnClick(view);
            }
        }
    }

    public void btnMaisOnClick(View view) {
        processaSinal("+");
    }

    public void btnMenosOnClick(View view) {
        processaSinal("-");
    }

    public void btnDivisaoOnClick(View view) {
        processaSinal("÷");
    }

    public void btnMultiplicaOnClick(View view) {
        processaSinal("x");
    }

    public void btnIgualOnClick(View view) {
        processaSinal("=");
    }

    public void btnLimpaOnClick(View view) {
        esquerda = null;
        operador = null;
        direita = null;

        isEsquerda = true;

        lblVisor.setText(EMPTY);
        // lblVisorPrincipal.setText(EMPTY);

    }

//    private void adicionaNumero(String numeroAsString) {
//        if (esquerda == null) {
//            esquerda += esquerda + numeroAsString;
//        } else {
//            direita += direita + numeroAsString;
//        }
//    }

    private void processaNumero(String numero) {
        if ((jaFezCalculo) && (isEsquerda) && (EMPTY.equals(lblVisorPrincipal.getText().toString()))) {
            lblVisorPrincipal.setText(lblVisor.getText().toString());
            lblVisor.setText(EMPTY);
        }
        lblVisor.setText(lblVisor.getText().toString() + numero);
    }

    private void processaSinal(String sinal) {
        if (isEsquerda) {
            if ("=".equals(sinal)) {
                showToastMsg("Sem operador!");
                return;
            } else {
                isEsquerda = false;
                if ((total != null) && (EMPTY.equals(lblVisorPrincipal.getText().toString()))) {
                    esquerda = total;
                    lblVisorPrincipal.setText(lblVisor.getText().toString());
                    lblVisor.setText(total);
                } else {
                    esquerda = lblVisor.getText().toString();
                }

//            lblVisor.setText(EMPTY);
//            lblVisorPrincipal.setText(EMPTY);

                lblVisor.setText(lblVisor.getText().toString() + sinal);
                operador = sinal;
            }
        } else {
            // ultimo digito nao for numero, entao eh sinal, entao substitui
            int tam = lblVisor.getText().toString().length();
            String strLblVisor = lblVisor.getText().toString();

            String ultChar = strLblVisor.substring(tam-1, tam);
            boolean ultCharIsSinal =  LISTA_OPERADORES.indexOf(ultChar) > 0 ? true : false;

            if ((!"=".equals(sinal)) && (lblVisor.getText().toString().length() > esquerda.length()) && (ultCharIsSinal)) {
                lblVisor.setText(lblVisor.getText().toString().substring(0, lblVisor.getText().toString().length()-1));
                lblVisor.setText(lblVisor.getText().toString() + sinal);
                operador = sinal;
            } else {
                direita = lblVisor.getText().toString().substring(esquerda.length()+1);

                switch (sinal) {
                    case "+":
                    case "-":
                    case "÷":
                    case "x":
                        operador = sinal;
                    case "=":
                        // valida esquerda
                        // valida direita
                        // valida operador

                        Long esq = Long.valueOf(esquerda);
                        Long dir = Long.valueOf(direita);
                        String sCalculoCompleto;

                        switch (operador) {
                            case "+":
                                total = String.valueOf(esq + dir);
                                sCalculoCompleto = esquerda + "+" + direita + "=" + total;
                                lblVisor.setText(sCalculoCompleto);
                                registraTotal(sCalculoCompleto);
                                break;
                            case "-":
                                total = String.valueOf(esq - dir);
                                sCalculoCompleto = esquerda + "-" + direita + "=" + total;
                                lblVisor.setText(sCalculoCompleto);
                                registraTotal(sCalculoCompleto);
                                break;
                            case "÷":
                                total = String.valueOf(esq / dir);
                                sCalculoCompleto = esquerda + "/" + direita + "=" + total;
                                lblVisor.setText(sCalculoCompleto);
                                registraTotal(sCalculoCompleto);
                                break;
                            case "x":
                                total = String.valueOf(esq * dir);
                                sCalculoCompleto = esquerda + "x" + direita + "=" + total;
                                lblVisor.setText(sCalculoCompleto);
                                registraTotal(sCalculoCompleto);
                                break;
                        }

                        lblVisorPrincipal.setText(EMPTY);
                        isEsquerda = true;

                }

                jaFezCalculo = true;
            }
        }
    }

    private void registraTotal(String sCalculoCompleto) {
        if ((selectedGroup != null) && (selectedGroup  > 0)) {
//            if (!EMPTY.equals(expListItems.get(selectedGroup-1).getName())) {
                // adiciona child
                if (expListItems.get(selectedGroup-1).getItems().size() <= MAX_SIMULACOES) {
                    expListItems.get(selectedGroup-1).getItems().add(0, new Child(sCalculoCompleto));
                }
                if (expListItems.get(selectedGroup-1).getItems().size() > MAX_SIMULACOES) {
                    expListItems.get(selectedGroup-1).getItems().remove(expListItems.get(selectedGroup-1).getItems().size() - 1);
                }
//            }
            expListItems.get(selectedGroup-1).setName(sCalculoCompleto);
            expListAdapter.notifyDataSetChanged();
        }
    }

    private void setActiveGroupButton() {
        btnG1.setBackgroundColor(getResources().getColor(selectedGroup == 1 ? R.color.colorPrimaryDark : (wasG1created ? R.color.colorPrimary : R.color.colorGroup)));
        btnG2.setBackgroundColor(getResources().getColor(selectedGroup == 2 ? R.color.colorPrimaryDark : (wasG2created ? R.color.colorPrimary : R.color.colorGroup)));
        btnG3.setBackgroundColor(getResources().getColor(selectedGroup == 3 ? R.color.colorPrimaryDark : (wasG3created ? R.color.colorPrimary : R.color.colorGroup)));
        btnG4.setBackgroundColor(getResources().getColor(selectedGroup == 4 ? R.color.colorPrimaryDark : (wasG4created ? R.color.colorPrimary : R.color.colorGroup)));
    }

    public void btnG1OnClick(View view) {
        selectedGroup = 1;

        if (!wasG1created) {
            // Setting Group 1
            ArrayList child_list = new ArrayList<Child>();
            Group gru1 = new Group("", false);

//            Child ch1_1 = new Child("");
//            child_list.add(ch1_1);
//
//            Child ch1_2 = new Child("");
//            child_list.add(ch1_2);
//
//            Child ch1_3 = new Child("");
//            child_list.add(ch1_3);

            gru1.setItems(child_list);

            expListItems.add(gru1);
            expListAdapter.notifyDataSetChanged();

            wasG1created = true;
            qtyGroups++;
        }

        setActiveGroupButton();
    }

    public void btnG2OnClick(View view) {
        selectedGroup = 2;

        if (!wasG2created) {
            // Setting Group 2
            ArrayList child_list = new ArrayList<Child>();
            Group gru2 = new Group("", false);

//            Child ch2_1 = new Child("");
//            child_list.add(ch2_1);
//
//            Child ch2_2 = new Child("");
//            child_list.add(ch2_2);
//
//            Child ch2_3 = new Child("");
//            child_list.add(ch2_3);

            gru2.setItems(child_list);

            expListItems.add(gru2);
            expListAdapter.notifyDataSetChanged();

            wasG2created = true;
            qtyGroups++;
        }

        setActiveGroupButton();
    }

    public void btnG3OnClick(View view) {
        selectedGroup = 3;

        if (!wasG3created) {
            // Setting Group 3
            ArrayList child_list = new ArrayList<Child>();
            Group gru3 = new Group("", false);

//            Child ch3_1 = new Child("");
//            child_list.add(ch3_1);
//
//            Child ch3_2 = new Child("");
//            child_list.add(ch3_2);
//
//            Child ch3_3 = new Child("");
//            child_list.add(ch3_3);

            gru3.setItems(child_list);

            expListItems.add(gru3);
            expListAdapter.notifyDataSetChanged();

            wasG3created = true;
            qtyGroups++;
        }

        setActiveGroupButton();
    }

    public void btnG4OnClick(View view) {
        selectedGroup = 4;

        if (!wasG4created) {
            // Setting Group 4
            ArrayList child_list = new ArrayList<Child>();
            Group gru4 = new Group("", false);

//            Child ch4_1 = new Child("");
//            child_list.add(ch4_1);
//
//            Child ch4_2 = new Child("");
//            child_list.add(ch4_2);
//
//            Child ch4_3 = new Child("");
//            child_list.add(ch4_3);

            gru4.setItems(child_list);

            expListItems.add(gru4);
            expListAdapter.notifyDataSetChanged();

            wasG4created = true;
            qtyGroups++;
        }

        setActiveGroupButton();
    }
}
