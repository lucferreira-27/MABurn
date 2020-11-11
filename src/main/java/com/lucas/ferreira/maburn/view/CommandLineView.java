//package com.lucas.ferreira.maburn.view;
//
//import java.util.List;
//import java.util.Scanner;
//
//import com.lucas.ferreira.maburn.model.bean.Anime;
//import com.lucas.ferreira.maburn.model.bean.Chapter;
//import com.lucas.ferreira.maburn.model.bean.Episode;
//import com.lucas.ferreira.maburn.model.bean.Manga;
//import com.lucas.ferreira.maburn.model.collections.Collections;
//import com.lucas.ferreira.maburn.model.enums.Category;
//import com.lucas.ferreira.maburn.model.itens.CollectionItem;
//import com.lucas.ferreira.maburn.model.loader.CollectionLoader;
//import com.lucas.ferreira.maburn.model.loader.SlowAnimeCollectionLoader;
//import com.lucas.ferreira.maburn.model.loader.SlowMangaCollectionLoader;
//
//public class CommandLineView implements View {
//
//	Scanner keybord = new Scanner(System.in);
//
//	@Override
//	public String definePath(String category) {
//		// TODO Auto-generated method stub
//		System.out.println("> Inform PATH for " + category + ": ");
//		return keybord.next();
//	}
//
//	@Override
//	public int informItensInColletion(Collections collections) {
//		// TODO Auto-generated method stub
//		System.out.println("==== COLLECTION ==== [" + collections.getDestination() + "]");
//		if (collections.getItens().isEmpty()) {
//			System.out.println("> [VAZIO]");
//			System.out.println("> [0][BACK]");
//			System.out.println("==========================================");
//			return select() - 1;
//		}
//		int index = 1;
//		for (CollectionItem item : collections.getItens()) {
//
//			System.out.println("[" + index + "] " + item.getName());
//			index++;
//		}
//		System.out.println("> [0][BACK]");
//		System.out.println("==================================");
//		
//		return select() - 1;
//
//	}
//
//	@Override
//	public int informChaptersInManga(Manga manga) {
//		// TODO Auto-generated method stub
//		Integer action = null;
//
//		System.out.println("==== MANGA ==== [" + manga.getName() + "]");
//		if (manga.getListChapters().isEmpty()) {
//			System.out.println("> [VAZIO]");
//			System.out.println("> [0][BACK]");
//			System.out.println("==========================================");
//			action = select() - 1;
//			return action;
//		}
//		int index = 1;
//		for (Chapter chapter : manga.getListChapters()) {
//
//			System.out.println("> " + chapter.getName());
//			index++;
//		}
//		System.out.println("> [0][BACK]");
//		System.out.println("==========================================");
//		action = select() - 1;
//		return action;
//
//	}
//
//	@Override
//	public int informEpisodesInAnime(Anime anime) {
//		// TODO Auto-generated method stub
//		Integer action = null;
//		System.out.println("==== ANIME ==== [" + anime.getName() + "]");
//		if (anime.getListEpisodes().isEmpty()) {
//			System.out.println("> [VAZIO]");
//			System.out.println("> [0][BACK]");
//			System.out.println("==========================================");
//			System.out.print("> ACTION: ");
//			action = select();
//			return action;
//		}
//		int index = 1;
//		for (Episode episode : anime.getListEpisodes()) {
//
//			System.out.println("> " + episode.getName());
//			index++;
//		}
//		System.out.println("> [0][BACK]");
//		System.out.println("==========================================");
//		System.out.print("> ACTION: ");
//		action = select() - 1;
//		return action;
//
//	}
//
//	@Override
//	public int selectItemInCollection() {
//		// TODO Auto-generated method stub
//		System.out.println("> INDEX: ");
//		return select() - 1;
//
//	}
//
//	public int select() {
//		return -1; //keybord.nextInt(); disable
//	}
//
//	@Override
//	public Category selectCollection() {
//		// TODO Auto-generated method stub
//		String animeCategory = "anime";
//		String mangaCategory = "manga";
//		System.out.println("> [0] Anime Collection");
//		System.out.println("> [1] Manga Collection");
//		System.out.print("> OPTION: ");
//		int option = select();
//		if (option > 1 || option < 0)
//			throw new IllegalArgumentException("Invalid input [range allowed 0 or 1]");
//
//		if (option == 1)
//			return Category.MANGA;
//		if (option == 0)
//			return Category.ANIME;
//		return null;
//
//	}
//
//	@Override
//	public int editConfig() {
//		// TODO Auto-generated method stub
//		System.out.println("> [1] EDIT PATH");
//		System.out.println("> [2] EDIT  ITEM IN COLLECTION");
//		System.out.println("> [3] BACK");
//		int option = select();
//		if (option > 1 || option < 0)
//			throw new IllegalArgumentException("Invalid input [range allowed 0 or 1]");
//
//		return option;
//
//	}
//
//	@Override
//	public int showMenu() {
//		// TODO Auto-generated method stub
//		System.out.println("> [1] RUN COLLECTION");
//		System.out.println("> [2] EDIT CONFIG");
//		System.out.println("> [3] CLOSE PROGRAM");
//		int option = select();
//		if (option > 3 || option < 1)
//			throw new IllegalArgumentException("Invalid input [range allowed 1 until 3]");
//
//		return option;
//	}
//
//	@Override
//	public int showEditMenu() {
//		// TODO Auto-generated method stub
//		System.out.println("> [1] EDIT COLLECTION DATES");
//		System.out.println("> [2] EDIT CONFIG");
//		System.out.println("> [3] BACK");
//		int option = select();
//		if (option > 3 || option < 1)
//			throw new IllegalArgumentException("Invalid input [range allowed 1 until 3]");
//
//		return option;
//	}
//
//}
