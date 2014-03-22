package br.com.caelum.task;

import java.util.List;

import org.json.JSONException;

import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.support.WebClient;
import br.com.caelum.converter.AlunoConverter;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class EnviaContatosTask extends AsyncTask<Object, Object, String> {
	private final String endereco = "http://www.caelum.com.br/mobile";
	private String jsonDeResposta;
	private Context context;
	private ProgressDialog progress;
	

	@Override
	protected String doInBackground(Object... params) {

		AlunoDAO dao = new AlunoDAO(context);
		List<Aluno> lista = dao.getLista();
		dao.close();
		
			String listaJSON = null;
			try {
				listaJSON = new AlunoConverter().toJson(lista);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonDeResposta = new WebClient(endereco).post(listaJSON);
	
		return jsonDeResposta;
	}

	protected void onPreExecute(){
		progress = ProgressDialog.show(context,"Aguarde","Envio de dados para a web",true,true);
	}
	
	@Override
	protected void onPostExecute(String result) {
		progress.dismiss();
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();
	}

}
