package services;

import abstractClasses.Funcionario;
import interfaces.FuncionarioDeAltoCargo;
import interfaces.IEmpresaService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Empresa implements IEmpresaService {

    private final List<Funcionario> funcionarios = new ArrayList<Funcionario>();

    @Override
    public void adicionarFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
    }

    @Override
    public double calcularSalario(int idFuncionario) {
        Predicate<Funcionario> byId = funcionario -> funcionario.getID() == idFuncionario;

        List<Funcionario> filteredFuncionario = this.funcionarios.stream().filter(byId).collect(Collectors.toList());

        if(filteredFuncionario.size() == 0) return -1.0;

        return filteredFuncionario.get(0).getSalario();
    }

    @Override
    public void aumentarAdicionalDosFuncionarios(double percentual_aumento) {

        boolean validPercentual = percentual_aumento >= 0.0 && percentual_aumento <= 1.0;

        if (validPercentual) {

            funcionarios.forEach(funcionario -> {
                if (funcionario instanceof FuncionarioDeAltoCargo) {
                    ((FuncionarioDeAltoCargo) funcionario).alteraAdicional(percentual_aumento);
                }
            });
        }
    }

    @Override
    public String relatorioDeFuncionarios() {

        if(funcionarios.size() == 0) return "Nenhum funcionário cadastrado";

        String funcionariosRelatorio = "";

        for (Funcionario funcionario : funcionarios) {
            String funcionarioStr = "";

            var lineSeparator = System.lineSeparator();

            funcionarioStr +=
                    "Id: " + String.valueOf(funcionario.getID())
                            .concat(lineSeparator)
                            .concat("Nome: ")
                            .concat(funcionario.getNome())
                            .concat(lineSeparator)
                            .concat("Salário: ")
                            .concat(String.valueOf(funcionario.getSalario()))
                            .concat(lineSeparator)
                            .concat("-------------------");

            funcionariosRelatorio += funcionarioStr.concat(lineSeparator);
        }

        return funcionariosRelatorio;
    }
}
