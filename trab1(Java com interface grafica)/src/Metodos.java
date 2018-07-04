
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.LinkedList;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Metodos {

    public static Mat converterRGBYIQ(Mat img) {
        Mat resultado = new Mat(img.rows(), img.cols(), CvType.CV_32FC3);

        double[] temp;
        double[] yiq = new double[3];

        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                temp = img.get(i, j);

                yiq[0] = 0.299 * temp[2] + 0.587 * temp[1] + 0.114 * temp[0];
                yiq[1] = 0.596 * temp[2] - 0.274 * temp[1] - 0.322 * temp[0];
                yiq[2] = 0.211 * temp[2] - 0.523 * temp[1] + 0.312 * temp[0];

                resultado.put(i, j, yiq);
            }
        }

        return resultado;
    }

    public static Mat converterYIQRGB(Mat img) {
        Mat resultado = new Mat(img.rows(), img.cols(), CvType.CV_8UC3);

        double[] temp;
        double[] rgb = new double[3];

        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                temp = img.get(i, j);
                rgb[2] = temp[0] + 0.956 * temp[1] + 0.621 * temp[2];
                rgb[1] = temp[0] - 0.272 * temp[1] - 0.647 * temp[2];
                rgb[0] = temp[0] - 1.106 * temp[1] + 1.703 * temp[2];

                //Verificação de limite RGB(0-255)
                if (rgb[2] < 0) {
                    rgb[2] = 0;
                } else if (rgb[2] > 255) {
                    rgb[2] = 255;
                }

                if (rgb[1] < 0) {
                    rgb[1] = 0;
                } else if (rgb[1] > 255) {
                    rgb[1] = 255;
                }

                if (rgb[0] < 0) {
                    rgb[0] = 0;
                } else if (rgb[0] > 255) {
                    rgb[0] = 255;
                }

                resultado.put(i, j, rgb);
            }
        }

        return resultado;
    }

    public static Mat bandaR(Mat img) {
        Mat resultado = new Mat(img.rows(), img.cols(), CvType.CV_8UC1);

        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double[] temp = img.get(i, j);
                resultado.put(i, j, temp[2]);
            }
        }

        return resultado;
    }

    public static Mat bandaG(Mat img) {
        Mat resultado = new Mat(img.rows(), img.cols(), CvType.CV_8UC1);

        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double[] temp = img.get(i, j);
                resultado.put(i, j, temp[1]);
            }
        }

        return resultado;
    }

    public static Mat bandaB(Mat img) {
        Mat resultado = new Mat(img.rows(), img.cols(), CvType.CV_8UC1);

        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double[] temp = img.get(i, j);
                resultado.put(i, j, temp[0]);
            }
        }

        return resultado;
    }

    public static Mat negativoRGB(Mat img) {
        Mat resultado = new Mat(img.rows(), img.cols(), CvType.CV_8UC3);

        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double[] temp = img.get(i, j);
                double[] negativo = new double[3];

                negativo[0] = 255 - temp[0];
                negativo[1] = 255 - temp[1];
                negativo[2] = 255 - temp[2];
                resultado.put(i, j, negativo);
            }
        }
        return resultado;
    }

    public static Mat negativoYIQ(Mat img) {
        Mat resultado = new Mat(img.rows(), img.cols(), CvType.CV_32FC3);

        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double[] YIQ = img.get(i, j);
                double[] negativo = new double[3];

                negativo[0] = 255 - YIQ[0];
                negativo[1] = YIQ[1];
                negativo[2] = YIQ[2];
                resultado.put(i, j, negativo);
            }
        }
        return resultado;
    }

    public static Mat brilhoSomaYIQ(Mat img, double AcrescimoBrilho) {
        Mat resultadoParcial = new Mat(img.rows(), img.cols(), CvType.CV_32FC3);
        double acrescimo = AcrescimoBrilho;

        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double[] YIQ = img.get(i, j);

                //Definir novo vetor YIQ, com mudança na variavel Y
                YIQ[0] = YIQ[0] + acrescimo;
                YIQ[1] = YIQ[1];
                YIQ[2] = YIQ[2];

                resultadoParcial.put(i, j, YIQ);
            }
        }
        return resultadoParcial;
    }

    public static Mat brilhoSomaRGB(Mat img, double AcrescimoBrilho) {
        Mat resultadoParcial = new Mat(img.rows(), img.cols(), CvType.CV_8UC3);
        double acrescimo = AcrescimoBrilho;

        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double[] rgb = img.get(i, j);

                //Definir novo vetor RGB, com mudança em cada uma das bandas
                rgb[0] = rgb[0] + acrescimo;
                rgb[1] = rgb[1] + acrescimo;
                rgb[2] = rgb[2] + acrescimo;

                if (rgb[2] < 0) {
                    rgb[2] = 0;
                } else if (rgb[2] > 255) {
                    rgb[2] = 255;
                }

                if (rgb[1] < 0) {
                    rgb[1] = 0;
                } else if (rgb[1] > 255) {
                    rgb[1] = 255;
                }

                if (rgb[0] < 0) {
                    rgb[0] = 0;
                } else if (rgb[0] > 255) {
                    rgb[0] = 255;
                }

                resultadoParcial.put(i, j, rgb);
            }
        }
        return resultadoParcial;
    }

    public static Mat brilhoMultiplicacaoYIQ(Mat img, double AcrescimoBrilho) {
        Mat resultadoParcial = new Mat(img.rows(), img.cols(), CvType.CV_32FC3);
        double acrescimo = AcrescimoBrilho;

        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double[] YIQ = img.get(i, j);

                //Definir novo vetor YIQ, com mudança na variavel Y
                YIQ[0] = YIQ[0] * acrescimo;
                YIQ[1] = YIQ[1];
                YIQ[2] = YIQ[2];
                resultadoParcial.put(i, j, YIQ);
            }
        }
        return resultadoParcial;
    }

    public static Mat brilhoMultiplicacaoRGB(Mat img, double AcrescimoBrilho) {
        Mat resultadoParcial = new Mat(img.rows(), img.cols(), CvType.CV_8UC3);
        double acrescimo = AcrescimoBrilho;

        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double[] rgb = img.get(i, j);

                //Definir novo vetor RGB, com mudança em cada uma das bandas
                rgb[0] = rgb[0] * acrescimo;
                rgb[1] = rgb[1] * acrescimo;
                rgb[2] = rgb[2] * acrescimo;

                if (rgb[2] < 0) {
                    rgb[2] = 0;
                } else if (rgb[2] > 255) {
                    rgb[2] = 255;
                }

                if (rgb[1] < 0) {
                    rgb[1] = 0;
                } else if (rgb[1] > 255) {
                    rgb[1] = 255;
                }

                if (rgb[0] < 0) {
                    rgb[0] = 0;
                } else if (rgb[0] > 255) {
                    rgb[0] = 255;
                }

                resultadoParcial.put(i, j, rgb);
            }
        }
        return resultadoParcial;
    }

    public static Mat limiarizaçãoY(Mat img, int limiar) {
        Mat resultado = new Mat(img.rows(), img.cols(), CvType.CV_8UC1); //Matriz com resultado.
        Mat YIQ = converterRGBYIQ(img); //Conversão RGB-YIQ.

        // Limiar com Y médio;
        if (limiar == -1) { // Restrição caso nao seja escolhido pelo usuario.
            limiar = 0;
            for (int i = 0; i < YIQ.rows(); i++) {
                for (int j = 0; j < YIQ.cols(); j++) {
                    double[] temp = YIQ.get(i, j);
                    limiar += temp[0];
                }
            }
            limiar = (int) (limiar / (img.rows() * img.cols())); //Novo limiar, caso não neja recebido pelo usuário.
        }

        // Limiarização
        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double[] temp = YIQ.get(i, j);

                if (temp[0] <= limiar) {
                    resultado.put(i, j, 0);
                } else {
                    resultado.put(i, j, 255);
                }
            }
        }

        return resultado;
    }

    public static Mat filtroMediana(Mat img, int tamK) {
        LinkedList<Double> KernelOrdenadoR = new LinkedList<Double>();
        LinkedList<Double> KernelOrdenadoG = new LinkedList<Double>();
        LinkedList<Double> KernelOrdenadoB = new LinkedList<Double>();
        int tamKernel = tamK * tamK; //Quantidade de elementos no Kernel;
        int nAddBordas = tamK / 2; //Numero de adições de bordas variando com o kernel dado pelo usuario;
        // System.out.println("Numero de repetições para colocar borda: " + nAddBordas);
        Mat resultado = new Mat(img.rows(), img.cols(), CvType.CV_8UC3);// Resultado pos filtragem.
        Mat MBordas = img; // Matriz para manipulação com bordas.
        Mat kernel = new Mat(tamK, tamK, CvType.CV_8UC3); //Kernel.
        //Adicionando bordas.
        for (int a = 0; a < nAddBordas; a++) {
            MBordas = addBordas(MBordas);
        }
        //Percorrendo os pixels da matriz original e realizando as medianass.
        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                //System.out.println("i: " + i + "j: " + j);// Posições do pixel atual.
                // Pegando o kernel.
                int aux = 0, aux2 = 0; //linha e coluna do kernel.            
                int v = j, k = i; // linha e coluna da minha matriz com bordas.                
                // System.out.println("Começando com v: " + v + " e k: " + k);
                //Preenchendo o kernel.
                while (aux2 != tamK) {
                    while (aux != tamK) {
                        //    System.out.printf("Colocando no kernel[%d][%d] o meu MOriginal[%d][%d]\n", aux2, aux, k, v);
                        double[] temp = MBordas.get(k, v);
                        kernel.put(aux2, aux, temp);
                        aux++;
                        v++;
                    }
                    aux = 0;
                    v = j;
                    k++;
                    aux2++;
                }
                int Mediana = (int) (Math.round(tamKernel / 2.0)); // Kernel sendo sempre um quadrado impar.
                // System.out.println("Mediana: " + Mediana + " Tam Kernel: " + tamKernel);
                double[] menor = kernel.get(0, 0); //Assumindo RGB do meu Kernel[0][0] como parametro de comparação inicial;
                int aux3 = 0; // variavel auxiliar para evitar comparação com o kernel[0][0];               
                KernelOrdenadoR.add(menor[0]); // BLUE do kernel[0][0];
                KernelOrdenadoG.add(menor[1]); // GREEN do kernel[0][0];
                KernelOrdenadoB.add(menor[2]); // RED do kernel[0][0];
                //Mediana do pixel.
                double[] Final = new double[3];
                for (int a = 0; a < tamK; a++) {
                    for (int b = 0; b < tamK; b++) {
                        double[] temp = kernel.get(a, b);
                        //ORDENA RED.
                        for (int x = 0; x < KernelOrdenadoR.size(); x++) {
                            if ((temp[2] <= KernelOrdenadoR.get(x)) && (aux3 != 0)) {
                                //System.out.println("Adicionei "+kernel[a][b]+" na posicao "+x);
                                KernelOrdenadoR.add(x, temp[2]);
                                break;
                            }
                            if ((aux3 != 0) && (x == KernelOrdenadoR.size() - 1)) {
                                // System.out.println("add no final");
                                KernelOrdenadoR.add(temp[2]);
                                break;
                            }

                        }
                        //ORDENA GREEN.
                        for (int x = 0; x < KernelOrdenadoG.size(); x++) {
                            if ((temp[1] <= KernelOrdenadoG.get(x)) && (aux3 != 0)) {
                                //System.out.println("Adicionei "+kernel[a][b]+" na posicao "+x);
                                KernelOrdenadoG.add(x, temp[1]);
                                break;
                            }
                            if ((aux3 != 0) && (x == KernelOrdenadoG.size() - 1)) {
                                // System.out.println("add no final");
                                KernelOrdenadoG.add(temp[1]);
                                break;
                            }
                            
                        }
                        //ORDENA BLUE.
                        for (int x = 0; x < KernelOrdenadoB.size(); x++) {
                            if ((temp[0] <= KernelOrdenadoB.get(x)) && (aux3 != 0)) {
                                //System.out.println("Adicionei "+kernel[a][b]+" na posicao "+x);
                                KernelOrdenadoB.add(x, temp[0]);
                                break;
                            }
                            if ((aux3 != 0) && (x == KernelOrdenadoB.size() - 1)) {
                                // System.out.println("add no final");
                                KernelOrdenadoB.add(temp[0]);
                                break;
                            }
                            aux3++;
                        }
                    }
                }
                Final[0] = KernelOrdenadoB.get(Mediana - 1);//mediana B
                Final[1] = KernelOrdenadoG.get(Mediana - 1);//mediana G
                Final[2] = KernelOrdenadoR.get(Mediana - 1);//mediana R
                MBordas.put(i + nAddBordas, j + nAddBordas, Final);//Atualiza MBordas para calculo dos pixels posteriores.
                resultado.put(i, j, Final); //insere no pixel atual a mediana de cada cor.
                //Limpa as LinkedLists para ordenar os pixels posteriores.
                KernelOrdenadoR.clear();
                KernelOrdenadoG.clear();
                KernelOrdenadoB.clear();

            }

        }
        return resultado;
    }

    public static Mat filtroMedia(Mat img, int tamK) {
        int tamKernel = tamK * tamK; //Quantidade de elementos no Kernel;
        int nAddBordas = tamK / 2; //Numero de adições de bordas variando com o kernel dado pelo usuario;
        //System.out.println("Numero de repetições para colocar borda: " + nAddBordas);

        Mat resultado = new Mat(img.rows(), img.cols(), CvType.CV_8UC3);// Resultado pos filtragem.
        Mat MBordas = img; // Matriz para manipulação com bordas.
        Mat kernel = new Mat(tamK, tamK, CvType.CV_8UC3); //Kernel.
        //Adicionando bordas.
        for (int a = 0; a < nAddBordas; a++) {
            MBordas = addBordas(MBordas);
        }
        //Percorrendo os pixels da matriz original e realizando as medias.
        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                //System.out.println("i: " + i + "j: " + j);// Posições do pixel atual.
                // Pegando o kernel.
                int aux = 0, aux2 = 0; //linha e coluna do kernel.            
                int v = j, k = i; // linha e coluna da minha matriz com bordas.                
                //System.out.println("Começando com v: " + v + " e k: " + k);
                //Preenchendo o kernel.
                while (aux2 != tamK) {
                    while (aux != tamK) {
                        //System.out.printf("Colocando no kernel[%d][%d] o meu MOriginal[%d][%d]\n", aux2, aux, k, v);
                        double[] temp = MBordas.get(k, v);
                        kernel.put(aux2, aux, temp);
                        aux++;
                        v++;
                    }
                    aux = 0;
                    v = j;
                    k++;
                    aux2++;
                }
                //Media do pixel.
                double[] total = new double[3];
                for (int a = 0; a < tamK; a++) {
                    for (int b = 0; b < tamK; b++) {
                        double[] temp = kernel.get(a, b);
                        total[0] += temp[0];//B
                        total[1] += temp[1];//G
                        total[2] += temp[2];//R
                    }
                }
                total[0] = total[0] / tamKernel;//media B
                total[1] = total[1] / tamKernel;// media G
                total[2] = total[2] / tamKernel;// media R 
                MBordas.put(i + nAddBordas, j + nAddBordas, total);//Atualiza MBordas para calculo dos pixels posteriores.
                resultado.put(i, j, total); //insere no pixel atual a media de cada cor.

            }
        }
        return resultado;
    }

    public static Mat addBordas(Mat img) {
        Mat MBordas = new Mat(img.rows() + 2, img.cols() + 2, CvType.CV_8UC3); //Ao add bordas a matriz ganha 2 linhas e 2 colunas.    
        
        for (int i = 0; i < MBordas.rows(); i++) {
            for (int j = 0; j < MBordas.cols(); j++) {
                // System.out.printf("i:%d, j: %d\n", i, j);
                if (i == 0) {//borda superior.
                    if (j == 0) {//diagonal esquerda superior.
                        double[] temp = img.get(i, j);
                        MBordas.put(i, j, temp);
                    } else if (j == img.cols() + 1) {//diagonal direita superior
                        double[] temp = img.get(i, img.cols() - 1);
                        MBordas.put(i, j, temp);
                    } else {
                        //demais bordas
                        double[] temp = img.get(i, j - 1);
                        MBordas.put(i, j, temp);
                    }
                } else if ((j == 0) && (i != 0) && (i != img.rows() + 1)) {//borda lateral esquerda
                    double[] temp = img.get(i - 1, j);
                    MBordas.put(i, j, temp);
                } else if ((j == img.cols() + 1) && (i != 0) && (i != img.rows() + 1)) {//borda lateral direita
                    double[] temp = img.get(i - 1, img.cols() - 1);
                    MBordas.put(i, j, temp);
                } else if (i == img.rows() + 1) {// borda inferior
                    if (j == 0) {// diagonal inferior esquerda
                        double[] temp = img.get(img.rows() - 1, j);
                        MBordas.put(i, j, temp);
                    } else if (j == img.cols() + 1) { // diagonal inferior direita
                        double[] temp = img.get(img.rows() - 1, img.cols() - 1);
                        MBordas.put(i, j, temp);
                    } else { // demas bordas inferiores
                        double[] temp = img.get(img.rows() - 1, j - 1);
                        MBordas.put(i, j, temp);
                    }
                } else {
                    // preenchendo o meio da matriz.
                    double[] temp = img.get(i - 1, j - 1);
                    MBordas.put(i, j, temp);
                }
            }
        }
        return MBordas;
    }
    
    public static Mat bordasSobel(Mat img){
        Mat resultado = new Mat(img.rows()-2, img.cols()-2, CvType.CV_8UC3);
        Mat mascaraHorizontal = new Mat(img.rows()-2, img.cols()-2, CvType.CV_8UC3);
        Mat mascaraVertical = new Mat(img.rows()-2, img.cols()-2, CvType.CV_8UC3);
        double temp[] = new double[3];
        
         for (int i = 1; i < img.rows()-1; i++) {
            for (int j = 1; j < img.cols()-1; j++) {
                temp[0] =  img.get(i-1, j-1)[0] + img.get(i, j-1)[0]*2 + img.get(i+1, j-1)[0]
                        + img.get(i-1, j+1)[0]*-1 + img.get(i, j+1)[0]*-2 + img.get(i+1, j-1)[0]*-1;
                temp[1] =  img.get(i-1, j-1)[1] + img.get(i, j-1)[1]*2 + img.get(i+1, j-1)[1]
                        + img.get(i-1, j+1)[1]*-1 + img.get(i, j+1)[1]*-2 + img.get(i+1, j-1)[1]*-1;
                temp[2] =  img.get(i-1, j-1)[2] + img.get(i, j-1)[2]*2 + img.get(i+1, j-1)[2]
                        + img.get(i-1, j+1)[2]*-1 + img.get(i, j+1)[2]*-2 + img.get(i+1, j-1)[2]*-1;
                mascaraHorizontal.put(i-1, j-1, temp);
                
                temp[0] =  img.get(i-1, j-1)[0]*-1 + img.get(i+1, j-1)[0] + img.get(i-1, j)[0]*-2
                        + img.get(i+1, j)[0]*2 + img.get(i-1, j+1)[0]*-1 + img.get(i+1, j+1)[0];
                temp[1] =  img.get(i-1, j-1)[1]*-1 + img.get(i+1, j-1)[1] + img.get(i-1, j)[1]*-2
                        + img.get(i+1, j)[1]*2 + img.get(i-1, j+1)[1]*-1 + img.get(i+1, j+1)[1];
                temp[2] =  img.get(i-1, j-1)[2]*-1 + img.get(i+1, j-1)[2] + img.get(i-1, j)[2]*-2
                        + img.get(i+1, j)[2]*2 + img.get(i-1, j+1)[2]*-1 + img.get(i+1, j+1)[2];
                mascaraVertical.put(i-1, j-1, temp);
                
                temp[0] = Math.sqrt(Math.pow(mascaraHorizontal.get(i-1,j-1)[0], 2) + Math.pow(mascaraVertical.get(i-1,j-1)[0], 2));
                temp[1] = Math.sqrt(Math.pow(mascaraHorizontal.get(i-1,j-1)[1], 2) + Math.pow(mascaraVertical.get(i-1,j-1)[1], 2));
                temp[2] = Math.sqrt(Math.pow(mascaraHorizontal.get(i-1,j-1)[2], 2) + Math.pow(mascaraVertical.get(i-1,j-1)[2], 2));
                resultado.put(i-1,j-1, temp);
                    
            }
         }
        
        return resultado;
    }
    
    public static Mat bordasLaplace(Mat img){
        Mat resultado = new Mat(img.rows()-2, img.cols()-2, CvType.CV_8UC3);
        double temp[] = new double[3];
        
         for (int i = 1; i < img.rows()-1; i++) {
            for (int j = 1; j < img.cols()-1; j++) {                   
                temp[0] = - img.get(i-1, j)[0] - img.get(i, j-1)[0] - img.get(i, j+1)[0] 
                        - img.get(i+1, j)[0] + img.get(i, j)[0]*4;
                temp[1] = - img.get(i-1, j)[1] - img.get(i, j-1)[1] - img.get(i, j+1)[1] 
                        - img.get(i+1, j)[1] + img.get(i, j)[1]*4;
                temp[2] = - img.get(i-1, j)[2] - img.get(i, j-1)[2] - img.get(i, j+1)[2] 
                        - img.get(i+1, j)[2] + img.get(i, j)[2]*4;

                resultado.put(i-1,j-1, temp);       
            }
         }
        
        return resultado;
    }
    
    public static Mat filtro1(Mat img){
        Mat resultado = new Mat(img.rows()-2, img.cols()-2, CvType.CV_8UC3);
        double temp[] = new double[3];
        
         for (int i = 1; i < img.rows()-1; i++) {
            for (int j = 1; j < img.cols()-1; j++) {                   
                temp[0] = - img.get(i-1, j)[0] - img.get(i, j-1)[0] - img.get(i, j+1)[0] 
                        - img.get(i+1, j)[0] + img.get(i, j)[0]*5;
                temp[1] = - img.get(i-1, j)[1] - img.get(i, j-1)[1] - img.get(i, j+1)[1] 
                        - img.get(i+1, j)[1] + img.get(i, j)[1]*5;
                temp[2] = - img.get(i-1, j)[2] - img.get(i, j-1)[2] - img.get(i, j+1)[2] 
                        - img.get(i+1, j)[2] + img.get(i, j)[2]*5;

                resultado.put(i-1,j-1, temp);       
            }
         }
        
        return resultado;
    }
    
     public static Mat filtro2(Mat img){
        Mat resultado = new Mat(img.rows()-2, img.cols()-2, CvType.CV_8UC3);
        double temp[] = new double[3];
        
         for (int i = 1; i < img.rows()-1; i++) {
            for (int j = 1; j < img.cols()-1; j++) {                   
                temp[0] = img.get(i, j)[0] - img.get(i-1, j-1)[0];
                temp[1] = img.get(i, j)[1] - img.get(i-1, j-1)[1];
                temp[2] = img.get(i, j)[2] - img.get(i-1, j-1)[2];

                resultado.put(i-1,j-1, temp);       
            }
         }
        
        return resultado;
    }

    public static Image toBufferedImage(Mat img) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (img.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        int bufferSize = img.channels() * img.cols() * img.rows();
        byte[] b = new byte[bufferSize];
        img.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(img.cols(), img.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
}
