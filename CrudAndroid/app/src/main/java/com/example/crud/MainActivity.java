package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.crud.Interface.CrudEmpleadoInterface;
import com.example.crud.model.Empleado;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<Empleado> listEmpleado;

    CrudEmpleadoInterface crudempleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Empleado emple = new Empleado(2L,"farid99","321farid","321farid@gmail.com");

        //guardarEmpleado(emple); // Guardar Registro
        //actualizarEmpleado(emple); //Actualizar Registro
       eliminarEmpleado(2l); //Eliminar Registro

        getAll();
    }

    private void getAll(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.12:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        crudempleado = retrofit.create(CrudEmpleadoInterface.class);
                Call<List<Empleado>> call = crudempleado.getAll();

        call.enqueue(new Callback<List<Empleado>>(){
            @Override
            public void onResponse(Call<List<Empleado>> call, Response<List<Empleado>> response){
                if(!response.isSuccessful()){
                    //System.out.println(response.message());
                    Log.e("Response err:,", response.message());
                    return;
                }
                listEmpleado = response.body();
                //listEmpleado.forEach(p-> System.out.println(p.toString()));
                listEmpleado.forEach(p-> Log.i("Empleados: ", p.toString()));

            }

            @Override

            public void onFailure(Call<List<Empleado>> call, Throwable t){
                System.out.println(t.getMessage());
            }

        });

    }

    private void guardarEmpleado(Empleado empleado){

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("http://192.168.0.12:8081/")

                .addConverterFactory(GsonConverterFactory.create())

                .build();

        crudempleado = retrofit.create(CrudEmpleadoInterface.class);

        Call call = crudempleado.guardarEmpleado(empleado);


        call.enqueue(new Callback() {


            @Override

            public void onResponse(Call call, Response response){

                if(!response.isSuccessful()){

                //System.out.println(response.message());

                    Log.e("Response error:,", response.message());

                    return;

                }

                Empleado empleado = (Empleado) response.body();

                Log.e("Empleado creado:", empleado.getNombre());

            //callMain();

            }


            @Override

            public void onFailure(Call call, Throwable t){

            //System.out.println(t.getMessage());

                Log.e("Trow error: ", t.getMessage());

            }


        });

    }

    private void actualizarEmpleado(Empleado empleado){


        Gson gson = new GsonBuilder()

                .setLenient()

                .create();


        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("http://192.168.0.12:8081/")

                .addConverterFactory(GsonConverterFactory.create())

                .build();

        crudempleado = retrofit.create(CrudEmpleadoInterface.class);

        Long id = empleado.getId();

        Call call = crudempleado.actualizarEmpleado(id,empleado);


        call.enqueue(new Callback() {


            @Override

            public void onResponse(Call call, Response response){

                if(!response.isSuccessful()){

                //System.out.println(response.message());

                    //Log.e("Response error:,", response.message());

                    return;

                }

                Empleado empleado = (Empleado) response.body();

                Log.e("**UPDATE** Nombre empleado editado: ", empleado.getNombre());

            }


            @Override

            public void onFailure(Call call, Throwable t){

            //System.out.println(t.getMessage());

                //Log.e("Throw error: ", t.getMessage());

            }


        });

    }

    private void eliminarEmpleado(Long id){


        Gson gson = new GsonBuilder()

                .setLenient()

                .create();


        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("http://192.168.0.12:8081/")

                .addConverterFactory(GsonConverterFactory.create())

                .build();

        crudempleado = retrofit.create(CrudEmpleadoInterface.class);


        Call call = crudempleado.eliminarEmpleado(id);


        call.enqueue(new Callback() {


            @Override

            public void onResponse(Call call, Response response){

                if(!response.isSuccessful()){

                //System.out.println(response.message());

                    Log.e("Response error:,", response.message());

                    return;

                }

                Empleado empleado = (Empleado) response.body();

                Log.e("**DELETE** Nombre empleado eliminado: ", empleado.getNombre());

            }


            @Override

            public void onFailure(Call call, Throwable t){

            //System.out.println(t.getMessage());

                //Log.e("Throw error: ", t.getMessage());

            }


        });

    }

}